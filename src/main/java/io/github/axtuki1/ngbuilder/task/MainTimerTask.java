package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.Utility;
import io.github.axtuki1.ngbuilder.comparator.RankComparator;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.GameData;
import io.github.axtuki1.ngbuilder.system.NGData;
import io.github.axtuki1.ngbuilder.system.ThemeData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class MainTimerTask extends BaseTimerTask {

    private ScoreboardManager manager;
    private ThemeData currentThemeData;
    private NGData currentNGData;
    private PlayerData builderPlayerData;
    private BlockCountTask blockCountTask;
    private boolean endProcessing;
    private DecimalFormat df;

    public MainTimerTask(NGBuilder pl, long sec) {
        super(pl, sec);
        if( sec == -1 ){
            isInfinity = true;
        } else {
            isInfinity = false;
        }
    }

    private static boolean isInfinity = false;

    @Override
    public void start() {
        df = new DecimalFormat("##0.00%");
        setSecondsRest(getSecondsMax());
        manager = Bukkit.getScoreboardManager();
        super.start();
        ArrayList<PlayerData> nonBuiltPlayers = new ArrayList<>();

        endProcessing = false;

        blockCountTask = null;

        if( GamePlayers.getBuiltPlayers().size() == 0 ){
            GameData.decrementCycle();
        }

        GameData.setRound( GameData.getRound() + 1 );

        // 建築者の選出
        for( PlayerData pd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
            pd.setBuilder(false);
            if(pd.getPlayer() != null){
                pd.getPlayer().getInventory().clear();
            }
            if( !GamePlayers.getBuiltPlayers().contains(pd.getUUID()) ){
                nonBuiltPlayers.add(pd);
            }
            GamePlayers.setData(pd.getUUID(), pd);
        }
        builderPlayerData = nonBuiltPlayers.get( Utility.generateRandom( nonBuiltPlayers.size() ) );
        GamePlayers.addBuiltPlayer(builderPlayerData.getUUID());
        builderPlayerData.setBuilder(true);
        GamePlayers.setData(builderPlayerData.getUUID(), builderPlayerData);



        //お題の選出
        if( GameConfig.AllowAllGenre.getBoolean() ){
            List<ThemeData> themeDataList = GameConfig.ThemeDataList.getThemeListFromDifficulty(
                    GameConfig.DifficultyMin.getInt(),
                    GameConfig.DifficultyMax.getInt()
            );
            currentThemeData = themeDataList.get(Utility.generateRandom(themeDataList.size()));

            if( GameData.getBeforeThemeData() != null ){
                while( GameData.getBeforeThemeData().equals(currentThemeData) ){
                    currentThemeData = themeDataList.get(Utility.generateRandom(themeDataList.size()));
                }
            }
            GameData.setBeforeThemeData(currentThemeData);
        } else {
            List<ThemeData> themeDataList = GameConfig.ThemeDataList.getThemeListFromGenre(
                    GameConfig.AllowGenre.getStringList()
            );

            currentThemeData = themeDataList.get(Utility.generateRandom(themeDataList.size()));

            if( GameData.getBeforeThemeData() != null ){
                while( GameData.getBeforeThemeData().equals(currentThemeData) ){
                    currentThemeData = themeDataList.get(Utility.generateRandom(themeDataList.size()));
                }
            }
            GameData.setBeforeThemeData(currentThemeData);
        }

//        currentThemeData = GameConfig.ThemeList.getStringList().get( Utility.generateRandom(GameConfig.ThemeList.getStringList().size()) );
        List<NGData> ngList = new ArrayList<>();
        for( NGData ngData : NGData.values() ){
            for (int i = 0; i < ngData.getPriority(); i++) {
                ngList.add(ngData);
            }
        }

        currentNGData = ngList.get( Utility.generateRandom( ngList.size() ) );
        if( GameData.getBeforeNGData() != null ){
            while ( GameData.getBeforeNGData().equals(currentNGData) ){
                currentNGData = ngList.get( Utility.generateRandom( ngList.size() ) );
            }
        }
        GameData.setBeforeNGData(currentNGData);

        if( getCurrentNGData().getNGMode().equals(NGData.NGMode.CountDeny) ){
            blockCountTask = new BlockCountTask(getPlugin());
            getCurrentNGData().genCount();
            blockCountTask.start(1,1);
        }

        makeScoreBoard();

        new BukkitRunnable(){
            @Override
            public void run() {
                Utility.AreaCleaner(
                        GameConfig.canBuilderPlacePoint1.getLocation(),
                        GameConfig.canBuilderPlacePoint2.getLocation()
                );

                GameConfig.canBuilderPlacePoint1.getLocation().getWorld().getEntities().forEach(entity -> {
                    if( !(entity instanceof Player) ){
                        if( Utility.isLocationArea(entity.getLocation(),
                                GameConfig.canBuilderPlacePoint1.getLocation(),
                                GameConfig.canBuilderPlacePoint2.getLocation())
                        ){
                            entity.remove();
                        }
                    }
                });
            }
        }.runTask(NGBuilder.getMain());

        Bukkit.broadcastMessage(ChatColor.RED + "=========================="+ChatColor.GREEN+"[" + ChatColor.AQUA + "ROUND " + GameData.getRound() + ChatColor.GREEN + "]"+ChatColor.RED+"==========================");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("   "+ ChatColor.YELLOW +"建築者   "+ChatColor.GREEN+": " + ChatColor.WHITE + builderPlayerData.getName());
        Bukkit.broadcastMessage("   "+ ChatColor.YELLOW +"ジャンル "+ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getGenre());
        Bukkit.broadcastMessage("   "+ ChatColor.YELLOW +"難易度   "+ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getDifficulty());
        builderPlayerData.getPlayer().sendMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentThemeData.getTheme());
        builderPlayerData.getPlayer().sendMessage("   " + ChatColor.YELLOW + "制約     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentNGData.getName() + ChatColor.YELLOW + " ["+df.format(currentNGData.getPriorityPer())+"]");
        builderPlayerData.getPlayer().sendMessage("   " + ChatColor.YELLOW + "減点     " + ChatColor.GREEN + ": " + ChatColor.WHITE + "-" + currentNGData.getPenalty());
        if(currentNGData.getBonus() > 1){
            builderPlayerData.getPlayer().sendMessage("   " + ChatColor.GOLD + ChatColor.BOLD.toString() + "NGBonusあり！ x" + currentNGData.getBonus());
        }
        if( currentNGData.getDescription() != null ){
            Bukkit.broadcastMessage("   "+currentNGData.getDescription());
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.RED +"============================================================");
        builderPlayerData.getPlayer().sendMessage(ChatColor.AQUA + "/cleanで白紙に戻すことができます。");
        for( Player p : Bukkit.getOnlinePlayers() ){
            PlayerData pd = GamePlayers.getData(p);
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder() ){
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendTitle(
                            "お題: "+ ChatColor.GREEN + currentThemeData.getTheme() ,
                            ChatColor.LIGHT_PURPLE + currentNGData.getName(),
                            10,
                            20*3,
                            20
                    );
                    p.teleport( GameConfig.BuilderSpawnPoint.getLocation().add(0.5, 0, 0.5) );
                    ItemStack item = new ItemStack(Material.BARRIER);
                    p.getInventory().setItem(9, item);
                    p.setFlying(false);
                } else {
                    if( Utility.isLocationArea(p.getLocation(),
                            GameConfig.canBuilderPlacePoint1.getLocation(),
                            GameConfig.canBuilderPlacePoint2.getLocation())
                    ){
                        p.teleport(NGBuilder.getWorld().getSpawnLocation().add(0.5, 0, 0.5));
                    }
                    p.setGameMode(GameMode.ADVENTURE);
                    p.setAllowFlight(true);
                    p.sendTitle( ChatColor.GREEN + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "ROUND " + GameData.getRound()  ,
                            "建築者: " + builderPlayerData.getName(),
                            10,
                            20*3,
                            20
                    );
                }
            }
        }
        if( isInfinity ){
//            JinroScoreboard.getScoreboard().resetScores(
//                    Utility.getColor(0, getSecondsMax()) + "残り時間: 0秒"
//            );
//            JinroScoreboard.getScoreboard().resetScores(
//                    Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: "+ getSeconds() +"秒"
//            );
//            JinroScoreboard.getScoreboard().resetScores(
//                    ChatColor.GREEN + "残り時間: ∞"
//            );
//            JinroScoreboard.getInfoObj().getScore(
//                    ChatColor.GREEN + "制約時間なし"
//            ).setScore(0);
            pause();
        }
    }

    public PlayerData getBuilderPlayerData() {
        return builderPlayerData;
    }

    public ThemeData getCurrentThemeData() {
        return currentThemeData;
    }

    public NGData getCurrentNGData() {
        return currentNGData;
    }

    public void setCurrentNGData(NGData currentNGData) {
        this.currentNGData = currentNGData;
        if( getCurrentNGData().getNGMode().equals(NGData.NGMode.CountDeny) ){
            getCurrentNGData().genCount();
            blockCountTask = new BlockCountTask(getPlugin());
            blockCountTask.start(1,1);
        } else if( blockCountTask != null ){
            blockCountTask.stop();
            blockCountTask.cancel();
            blockCountTask = null;
        }
    }

    @Override
    public void beforeView() {
        if( !isInfinity ){
//            JinroScoreboard.getScoreboard().resetScores(
//                    Utility.getColor(0, getSecondsMax()) + "残り時間: 0秒"
//            );
//            JinroScoreboard.getScoreboard().resetScores(
//                    Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: "+ getSeconds() +"秒"
//            );
        }
    }

    private int nextCount;

    @Override
    public void updateView() {
        HashMap<UUID, PlayerData> pls = GamePlayers.getPlayers();

        makeScoreBoard();

        if( isInfinity ){
            if( nextCount == 20 ){
                incrementElapse();
                nextCount = 0;
            } else {
                nextCount++;
            }
        }

        if( isInfinity ) {
            pause();
        }
    }

    boolean s = true;

    @Override
    public void execSecond() {
        if( getSeconds() == 60 ){
            for( Player p : Bukkit.getOnlinePlayers() ){
                p.sendTitle(ChatColor.GOLD + "残り 1分！" , "", 10, 40, 10);
            }
            Utility.playSoundToAllPlayer(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Utility.playSoundToAllPlayer(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Utility.playSoundToAllPlayer(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }
                    }.runTaskLater(NGBuilder.getMain(), 2);
                }
            }.runTaskLater(NGBuilder.getMain(), 2);
        }
    }

    public void makeScoreBoard(){
        makeScoreBoard(null);
    }

    public void makeScoreBoard( List<String> addList ){
        int Player_Count = 0,
                Spectator_Count = 0;

        for( Player p : Bukkit.getOnlinePlayers() ){
            PlayerData pd = GamePlayers.getData(p.getUniqueId());
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                Player_Count++;
            } else if(pd.getPlayingType().equals(PlayerData.PlayingType.Spectator)) {
                Spectator_Count++;
            }
        }

        for( Player p : Bukkit.getOnlinePlayers() ){

            Scoreboard board = p.getScoreboard();

            Objective ob = board.getObjective("GameInfo");

            if( ob != null ){
                ob.unregister();
            }

            ob = board.registerNewObjective("GameInfo","dummy");

            ob.setDisplaySlot(DisplaySlot.SIDEBAR);

            ob.setDisplayName("情報 ["+ Utility.getPingString(p)+ChatColor.WHITE+"]");


            PlayerData pd = GamePlayers.getData(p);

            LinkedHashSet<String> set = new LinkedHashSet<String>();

            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( !pd.isHiddenInfo() ){
                    if( pd.isBuilder() ){
                        set.add("お題: " + ChatColor.GREEN + currentThemeData.getTheme());
                        set.add("制約: " + ChatColor.GREEN + currentNGData.getShortName());
                        if( getCurrentNGData().getNGMode().equals(NGData.NGMode.CountDeny) && !isEndProcessing() ){
                            if( blockCountTask != null ){
                                set.add("総ブロック: " + ChatColor.GREEN + blockCountTask.getCount() + ChatColor.GRAY + "/" + ChatColor.AQUA + currentNGData.getCount());
                                if( blockCountTask.getCount() > currentNGData.getCount() ){
                                    NGEnd();
                                }
                            }
                        }
                    }
                }
                set.add("得点: " + ChatColor.GREEN + pd.getPoint());

//                if( getCurrentNGData() != null ){
//                    if( !getCurrentNGData().canUse(Utility.getItemInHand(p)) && Utility.getItemInHand(p).getType() != Material.AIR ){
//                        ActionBar.sendActionbar(p, ChatColor.RED + ChatColor.BOLD.toString() + "！！禁止対象です！！");
//                    } else {
//                        ActionBar.sendActionbar(p, "");
//                    }
//                }

            } else if( pd.getPlayingType().equals(PlayerData.PlayingType.GameMaster) ){
                set.add("役職: " + ChatColor.YELLOW + "[GameMaster]");
                set.add("参加人数: " + ChatColor.AQUA + Player_Count);
                set.add("観戦者数: " + ChatColor.AQUA + Spectator_Count);
                if( !pd.isHiddenInfo() ){
                    set.add("お題: " + ChatColor.GREEN + currentThemeData.getTheme());
                    set.add("制約: " + ChatColor.GREEN + currentNGData.getShortName());
                }
            } else if( pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) ){
//                set.add("役職: " + ChatColor.AQUA + "[Spectator]");
            } else {
                set.add("役職: [なし]");
            }

            if( Boolean.parseBoolean( String.valueOf(pd.getDebug().get("SeeNearPlayer")) ) ){

                Map.Entry<Player, Double> e = Utility.getNearPlayer(p);

                set.add(ChatColor.RED + "[D]" + ChatColor.WHITE + "Near Player:");
                if(e == null){
                    set.add("  Not Found");
                } else {
                    Map.Entry<Player, Double> block = e;
                    BigDecimal bd = new BigDecimal(block.getValue());
                    set.add("  " + block.getKey().getName() + "/"+bd.setScale(0, RoundingMode.HALF_UP)+"b");
                }
            }

            if( addList != null ){
                set.addAll(addList);
            }

            if( !isInfinity  ){
//                SimpleDateFormat formatter = new SimpleDateFormat("m:ss");
//                formatter.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
                set.add("残り時間: " + Utility.getColor(getSeconds(), getSecondsMax()) + Utility.convSecToTime((int) getSeconds()));
            }

            List<String> list = new ArrayList<>(set);

            for( String s : list ){
                if( (s+" ").length() >= 40 ){
                    System.out.println(s);
                    continue;
                }
                ob.getScore(
                        s + " "
                ).setScore(list.size() - list.indexOf(s) - 1);
            }

            p.setScoreboard(board);

        }
    }

    public void setSecondsRest(long secondsRest) {
        super.setSecondsRest(secondsRest);
        if( secondsRest == -1 ){
            isInfinity = true;
        } else {
            isInfinity = false;
        }
        if( isInfinity ){
//            JinroScoreboard.getScoreboard().resetScores(
//                    Utility.getColor(getSeconds(), getSecondsMax()) + "残り時間: "+ getSeconds() +"秒"
//            );
        }
    }

    @Override
    public void EndExec() {
        Bukkit.broadcastMessage(
                ChatColor.RED + "=======================" +
                        ChatColor.GREEN + "[" +
                        ChatColor.AQUA + "ROUND " +
                        GameData.getRound() +
                        ChatColor.WHITE + " - " +
                        ChatColor.YELLOW + "Result" +
                        ChatColor.GREEN + "]" +
                        ChatColor.RED + "=======================" );
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("   " + ChatColor.RED + "時間切れ！");
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW +"建築者   "+ChatColor.GREEN+": " + ChatColor.WHITE + builderPlayerData.getName());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW +"ジャンル "+ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getGenre());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW +"難易度   "+ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getDifficulty());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "お題    " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentThemeData.getTheme());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "制約    " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentNGData.getName() + ChatColor.YELLOW + " ["+df.format(currentNGData.getPriorityPer())+"]");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.RED +"===============================================================");
        Utility.playSoundToAllPlayer(Sound.ENTITY_BLAZE_DEATH, 1, 1);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle( ChatColor.GREEN + currentThemeData.getTheme()  ,
                    ChatColor.RED + "時間切れ！",
                    10,
                    20*3,
                    20
            );
        }
        nextAction();
    }

    public void CorrectEnd(Player player){
        PlayerData pd = GamePlayers.getData(player);
        String bo = (currentThemeData.getBonusPer() > 1 ? " x"+currentThemeData.getBonusPer() : "") +
                (currentThemeData.getBonusAdd() > 0 ? " +"+currentThemeData.getBonusAdd() : "");
        player.sendMessage(
                NGBuilder.getPrefix() + ChatColor.GREEN + "正解! " + ChatColor.GOLD + ChatColor.BOLD + "+" + (int)(getSeconds() *  1.2) + " " +
                        (
                                bo.length() == 0 ? "": "お題ボーナス！" + bo
                        )
        );
        // 回答
        int addAn = (int) (((int) getSeconds() * 1.2) * currentThemeData.getBonusPer() + currentThemeData.getBonusAdd());
        pd.addPoint(addAn);
//        Bukkit.broadcastMessage(pd.getName() + " +"+add);
        GamePlayers.setData(pd.getUUID(), pd);
        // 建築
        int addBu = (int) ((int) getSeconds() * currentNGData.getBonus());
//        Bukkit.broadcastMessage(builderPlayerData.getName() + " +"+add);
        builderPlayerData.addPoint(addBu);
        GamePlayers.setData(builderPlayerData.getUUID(), builderPlayerData);
        Bukkit.broadcastMessage(
                ChatColor.RED + "=======================" +
                        ChatColor.GREEN + "[" +
                        ChatColor.AQUA + "ROUND " +
                        GameData.getRound() +
                        ChatColor.WHITE + " - " +
                        ChatColor.YELLOW + "Result" +
                        ChatColor.GREEN + "]" +
                        ChatColor.RED + "=======================" );
        Bukkit.broadcastMessage("");
        if( currentNGData.getBonus() > 1 ){
            Bukkit.broadcastMessage("   " + ChatColor.GOLD + ChatColor.BOLD.toString() + "NGBonus！ x"+currentNGData.getBonus());
        }
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "建築者   " + ChatColor.GREEN+": " + ChatColor.WHITE + builderPlayerData.getName() + ChatColor.GOLD + " +"+addBu );
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "ジャンル " + ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getGenre());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "難易度   " + ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getDifficulty());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentThemeData.getTheme());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "制約     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentNGData.getName() + ChatColor.YELLOW + " ["+df.format(currentNGData.getPriorityPer())+"]");
        if( bo.length() != 0 ){
            Bukkit.broadcastMessage("   " + ChatColor.GOLD + ChatColor.BOLD.toString() + "ThemeBonus！" + bo);
        }
        Bukkit.broadcastMessage("   " + ChatColor.GREEN + "正解者" + ChatColor.YELLOW + ": " + ChatColor.WHITE + player.getName() + ChatColor.GOLD + " +"+addAn);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.RED +"===============================================================");
        Utility.playSoundToAllPlayer(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle( ChatColor.GREEN + currentThemeData.getTheme()  ,
                    "正解者: " + player.getName(),
                    10,
                    20*3,
                    20
            );
        }
        nextAction();
    }

    public void NGEnd(){
        builderPlayerData.getPlayer().sendMessage(NGBuilder.getPrefix() + ChatColor.YELLOW + "制約無視 " + ChatColor.RED + ChatColor.BOLD + "-" + currentNGData.getPenalty());
        builderPlayerData.removePoint(currentNGData.getPenalty());
        GamePlayers.setData(builderPlayerData.getUUID(), builderPlayerData);
        Bukkit.broadcastMessage(
                ChatColor.RED + "=======================" +
                        ChatColor.GREEN + "[" +
                        ChatColor.AQUA + "ROUND " +
                        GameData.getRound() +
                        ChatColor.WHITE + " - " +
                        ChatColor.YELLOW + "Result" +
                        ChatColor.GREEN + "]" +
                        ChatColor.RED + "=======================" );        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "建築者   " + ChatColor.GREEN+": " + ChatColor.WHITE + builderPlayerData.getName());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "ジャンル " + ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getGenre());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "難易度   " + ChatColor.GREEN+": " + ChatColor.WHITE + currentThemeData.getDifficulty());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentThemeData.getTheme());
        Bukkit.broadcastMessage("   " + ChatColor.YELLOW + "制約     " + ChatColor.GREEN + ": " + ChatColor.WHITE + currentNGData.getName() + ChatColor.YELLOW + " ["+df.format(currentNGData.getPriorityPer())+"]");
        Bukkit.broadcastMessage("   " + ChatColor.RED + "制約無視の為失格" + ChatColor.WHITE + ": " + ChatColor.RED + ChatColor.BOLD + "-" + currentNGData.getPenalty());
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.RED +"===============================================================");
        Utility.playSoundToAllPlayer(Sound.BLOCK_ANVIL_PLACE, 1, 0);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle( ChatColor.GREEN + currentNGData.getName()  ,
                    ChatColor.RED + "制約無視",
                    10,
                    20*3,
                    20
            );
        }
        nextAction();
    }

    public void nextAction(){
        pause();
        endProcessing = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                NGBuilder.getTask().stop();
                cancel();
                if (GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player).size() == GamePlayers.getBuiltPlayers().size()) {
                    if( GameData.getCycle() == 0 ){
                        Result();
                        NGBuilder.setTask(null);
                    } else {
                        GamePlayers.resetBuiltPlayers();
                        Bukkit.broadcastMessage(
                                ChatColor.RED + "==========================" +
                                        ChatColor.GREEN + "[" +
                                        ChatColor.AQUA + (GameData.getMaxCycle() - (GameData.getCycle() - 1)) + ChatColor.GRAY + "/" + ChatColor.AQUA + GameData.getMaxCycle()+"巡目" +
                                        ChatColor.GREEN + "]" +
                                        ChatColor.RED + "=========================="
                        );
                        for( Player p : Bukkit.getOnlinePlayers() ){
                            p.sendTitle(ChatColor.GREEN + "==== " + (GameData.getMaxCycle() - (GameData.getCycle() - 1)) + "巡目 ====", "" , 10, 40, 10);
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                builderPlayerData.getPlayer().teleport( NGBuilder.getWorld().getSpawnLocation().add(0.5,0,0.5) );
                                BaseTimerTask task = new MainTimerTask(
                                        NGBuilder.getMain(),
                                        getSecondsMax()
                                );
                                task.start();
                                NGBuilder.setTask(task);
                            }
                        }.runTaskLater(NGBuilder.getMain(), 60);
                    }
                } else {
                    builderPlayerData.getPlayer().teleport( NGBuilder.getWorld().getSpawnLocation().add(0.5,0,0.5) );
                    BaseTimerTask task = new MainTimerTask(
                            NGBuilder.getMain(),
                            getSecondsMax()
                    );
                    task.start();
                    NGBuilder.setTask(task);
                }
            }
        }.runTaskLater(NGBuilder.getMain(), 5 * 20);
    }

    public boolean isEndProcessing() {
        return endProcessing;
    }

    @Override
    public void exec() {
    }

    @Override
    public void onChat(AsyncPlayerChatEvent e) {
        Bukkit.broadcastMessage( "<"+e.getPlayer().getName()+"> " + e.getMessage() );
        if( !builderPlayerData.getUUID().equals(e.getPlayer().getUniqueId()) ){
            if( getCurrentThemeData().getTheme().equalsIgnoreCase( e.getMessage() ) ){
                CorrectEnd(e.getPlayer());
            }
        }
    }

    public void Result(){
        GameStatus.setStatus(GameStatus.End);
        for( Player p : Bukkit.getOnlinePlayers() ){

            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective ob = board.getObjective("GameInfo");

            if( ob != null ){
                ob.unregister();
            }

            ob = board.registerNewObjective("GameInfo","dummy");

            ob.setDisplaySlot(DisplaySlot.SIDEBAR);
            ob.setDisplayName("情報");

            PlayerData pd = GamePlayers.getData(p);

            LinkedHashSet<String> set = new LinkedHashSet<String>();

            ob.setDisplayName(ChatColor.RED + "=====" + ChatColor.YELLOW + "[" + ChatColor.GREEN + "GAME END" + ChatColor.YELLOW + "]" + ChatColor.RED + "=====");

            List<String> list = new ArrayList<>(set);

            for( PlayerData apd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
                ob.getScore(
                        apd.getName()
                ).setScore(apd.getPoint());
            }

            p.setScoreboard(board);

            p.teleport( NGBuilder.getWorld().getSpawnLocation().add(0.5,0,0.5) );


            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(false);
                p.getInventory().clear();
                p.teleport( NGBuilder.getWorld().getSpawnLocation().add(0.5,0,0.5) );
            }
        }

        ArrayList<Integer> out = new ArrayList<>();

        for( PlayerData pd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
//            if( !out.contains(pd.getPoint()) ){
                out.add(pd.getPoint());
//            }
        }

        out.sort(new RankComparator());

        int rankLimit = 5;

        List<ChatColor> color = Arrays.asList(ChatColor.YELLOW, ChatColor.AQUA, ChatColor.GREEN);

        HashMap<Integer,ArrayList<UUID>> ranking = new HashMap<>();

        HashMap<UUID,Integer> rankingAll = new HashMap<>();

        for( PlayerData pd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
            int rank = out.indexOf(pd.getPoint()) + 1;
            ArrayList<UUID> uuids = ranking.get(rank);
            if( uuids == null ){
                uuids = new ArrayList<>();
            }
            uuids.add(pd.getUUID());
            ranking.put(rank,uuids);
            rankingAll.put(pd.getUUID(), rank);
        }

        Bukkit.broadcastMessage(
                ChatColor.RED + "==========================" +
                        ChatColor.GREEN + "[" +
                        ChatColor.AQUA + "結果発表" +
                        ChatColor.GREEN + "]" +
                        ChatColor.RED + "==========================" );
//        Bukkit.broadcastMessage("");
        for( int i = 1; i <= 5; i++ ){
            if( ranking.get(i) == null ){
                break;
            }
            for( UUID uuid : ranking.get(i) ){
                try {
                    Bukkit.broadcastMessage("   "+ color.get(i - 1)+i+"位" + ChatColor.GREEN+": " + ChatColor.WHITE + Bukkit.getPlayer(uuid).getName() + ChatColor.WHITE + " ("+GamePlayers.getData(uuid).getPoint()+")");
                } catch ( ArrayIndexOutOfBoundsException e){
                    Bukkit.broadcastMessage("   "+ ChatColor.WHITE+i+"位" + ChatColor.GREEN+": " + ChatColor.WHITE + Bukkit.getPlayer(uuid).getName() + ChatColor.WHITE + " ("+GamePlayers.getData(uuid).getPoint()+")");
                }
            }
        }
//        Bukkit.broadcastMessage("");
        for( PlayerData pd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
            int rank = rankingAll.get(pd.getUUID());
            pd.getPlayer().sendMessage("");
            pd.getPlayer().sendMessage("   "+ ChatColor.GOLD + rank+"位" + ChatColor.GREEN+": " + ChatColor.WHITE + pd.getName() + ChatColor.WHITE + " ("+pd.getPoint()+")");;
//            pd.getPlayer().sendMessage("");
        }
        Bukkit.broadcastMessage(ChatColor.RED +"===============================================================");


    }
}
