package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.*;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.BlockData;
import io.github.axtuki1.ngbuilder.system.GameData;
import io.github.axtuki1.ngbuilder.system.NGData;
import io.github.axtuki1.ngbuilder.task.BaseTask;
import io.github.axtuki1.ngbuilder.task.BaseTimerTask;
import io.github.axtuki1.ngbuilder.task.MainTimerTask;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

public class BuilderCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 0 ){
            if( sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sendAdminCmdHelp((Player)sender);
            } else {
                sendCmdHelp((Player)sender);
            }
            return true;
        }
        if( args[0].equalsIgnoreCase("ng") ){
            new BuilderNGCmd().onCommand(sender,command,label,args);
        }
        if( sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
            if( args[0].equalsIgnoreCase("start") ){
                if( GameStatus.getStatus().equals(GameStatus.End) ){
                    NGBuilder.init();
                }
                if( !GameStatus.getStatus().equals(GameStatus.Ready) ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "待機状態ではありません。/builder initで初期化を行って下さい。");
                    return true;
                }
                start(sender, command, label, args);
            } else if( args[0].equalsIgnoreCase("gmstart") ){
                if( GameStatus.getStatus().equals(GameStatus.End) ){
                    NGBuilder.init();
                }
                if( !GameStatus.getStatus().equals(GameStatus.Ready) ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "待機状態ではありません。/builder initで初期化を行って下さい。");
                    return true;
                }
                if( sender.hasPermission("Builder.GameMaster") ){
                    PlayerData pd = GamePlayers.getData(((Player)sender));
                    pd.setPlayingType(PlayerData.PlayingType.GameMaster);
                    GamePlayers.setData(pd.getUUID(), pd);
                }
                start(sender, command, label, args);
            } else if( args[0].equalsIgnoreCase("stop") ){
                if( NGBuilder.getTask() != null ){
                    NGBuilder.getTask().stop();
                    GameStatus.setStatus(GameStatus.End);
                    Bukkit.broadcastMessage(NGBuilder.getPrefix() + ChatColor.YELLOW + "ゲームを強制終了しました。");
                } else {
                    Bukkit.broadcastMessage(NGBuilder.getPrefix() + ChatColor.RED + "ゲームが実行中ではありません。");
                }
            } else if( args[0].equalsIgnoreCase("init") ){
                NGBuilder.init();
                Bukkit.broadcastMessage(NGBuilder.getPrefix() + "初期化しました。");
            } else if( args[0].equalsIgnoreCase("reload") ){
                NGBuilder.getMain().reloadConfig();
                GameConfig.ThemeDataList.reload();
                sender.sendMessage(NGBuilder.getPrefix() + "Configの再読込を行いました。");
            } else if(args[0].equalsIgnoreCase("c")) {
                BlockData bd = new BlockData(Utility.getItemInHand(((Player)sender)));
                sender.sendMessage( bd.getMaterial().toString() + ":" + bd.getDataValue() + " -> " + NGData.NetherAndEndOnly.canUse(bd));
            } else if(args[0].equalsIgnoreCase("loc")) {
                if (args.length == 2) {
                    Player p = (Player)sender;
                    if( args[1].equalsIgnoreCase("tp") ){
                        GameConfig.BuilderSpawnPoint.setLocation( p.getLocation() );
                        p.sendMessage(NGBuilder.getPrefix() + "建築者がTPする座標を設定しました。");
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("spawn")){
                        GameConfig.WorldSpawnPoint.setLocation( p.getLocation() );
                        p.sendMessage(NGBuilder.getPrefix() + "ワールドのスポーンポイントを設定しました。");
                        return true;
                    }
                }
                if (GamePlayers.getSettingPlayers().contains(((Player) sender).getUniqueId())) {
                    sender.sendMessage(NGBuilder.getPrefix() + "座標編集モードを終了しました。");
                    GamePlayers.removeSettingPlayer(((Player) sender).getUniqueId());
                } else {
                    Player p = ((Player) sender);
                    sender.sendMessage(NGBuilder.getPrefix() + "座標編集モードに入りました。");
                    GamePlayers.addSettingPlayer(p.getUniqueId());

                    // point1
                    ItemStack item = new ItemStack(Material.WOOL, 1, (byte) 6);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.LIGHT_PURPLE + "建築可能エリア Point1");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);

                    item = new ItemStack(Material.WOOL, 1, (byte) 11);
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.AQUA + "建築可能エリア Point2");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);

//                    item = new ItemStack( Material.WOOL,1, (byte)4);
//                    meta = item.getItemMeta();
//                    meta.setDisplayName(ChatColor.GOLD + "建築者TPポイント");
//                    item.setItemMeta(meta);
//                    p.getInventory().addItem(item);

                }
            } else if( args[0].equalsIgnoreCase("spec") ){
                new BuilderSpecCmd().onCommand(sender,command,label,args);
            } else if( args[0].equalsIgnoreCase("theme") ){
                new BuilderThemeCmd().onCommand(sender,command,label,args);
            } else if( args[0].equalsIgnoreCase("list") ){
                new BuilderPlayerListCmd().onCommand(sender,command,label,args);
            } else if( args[0].equalsIgnoreCase("debug") ){
                new BuilderDebugCmd().onCommand(sender,command,label,args);
            } else if( args[0].equalsIgnoreCase("option") ){
                new BuilderOptionCmd().onCommand(sender,command,label,args);
            } else if( args[0].equalsIgnoreCase("now") ){
                if( !GameStatus.getStatus().equals(GameStatus.Playing) ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "現在ゲーム中ではありません。");
                    return true;
                }
                DecimalFormat df = new DecimalFormat("##0.00%");
                sender.sendMessage(ChatColor.RED + "======================="+ChatColor.GREEN+"[" + ChatColor.AQUA + "ROUND " + GamePlayers.getBuiltPlayers().size() + ChatColor.GREEN + "]"+ChatColor.RED+"=======================");
                sender.sendMessage("");
                sender.sendMessage("   "+ ChatColor.YELLOW +"建築者   "+ChatColor.GREEN+": " + ChatColor.WHITE + ((MainTimerTask)NGBuilder.getTask()).getBuilderPlayerData().getName());
                sender.sendMessage("   "+ ChatColor.YELLOW +"ジャンル "+ChatColor.GREEN+": " + ChatColor.WHITE + ((MainTimerTask)NGBuilder.getTask()).getCurrentThemeData().getGenre());
                sender.sendMessage("   "+ ChatColor.YELLOW +"難易度   "+ChatColor.GREEN+": " + ChatColor.WHITE + ((MainTimerTask)NGBuilder.getTask()).getCurrentThemeData().getDifficulty());
                sender.sendMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + ((MainTimerTask)NGBuilder.getTask()).getCurrentThemeData().getTheme());
                sender.sendMessage("   " + ChatColor.YELLOW + "制約     " + ChatColor.GREEN + ": " + ChatColor.WHITE + ((MainTimerTask)NGBuilder.getTask()).getCurrentNGData().getName() + ChatColor.YELLOW + "["+df.format(((MainTimerTask)NGBuilder.getTask()).getCurrentNGData().getPriorityPer())+"]");
                sender.sendMessage("   " + ChatColor.YELLOW + "減点     " + ChatColor.GREEN + ": " + ChatColor.WHITE + "-" + ((MainTimerTask)NGBuilder.getTask()).getCurrentNGData().getPenalty());
                if(((MainTimerTask)NGBuilder.getTask()).getCurrentNGData().getBonus() > 1){
                    sender.sendMessage("   " + ChatColor.GOLD + ChatColor.BOLD.toString() + "NGBonusあり！ x" + ((MainTimerTask)NGBuilder.getTask()).getCurrentNGData().getBonus());
                }
                sender.sendMessage("");
                sender.sendMessage(ChatColor.RED +"========================================================");

            } else if( args[0].equalsIgnoreCase("clean") ){
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
                sender.sendMessage(NGBuilder.getPrefix() + "クリーンアップしました。");
            } else if( args[0].equalsIgnoreCase("timer") ) {
                // jinro_ad timer set 100
                if (args.length >= 3) {
                    if (args[1].equalsIgnoreCase("set")) {
                        BaseTask task = NGBuilder.getTask();
                        if (!(task instanceof BaseTimerTask)) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "実行中のタスクはタイマーが存在しません。");
                            return true;
                        }
                        ((BaseTimerTask) task).setSecondsRest(Integer.parseInt(args[2]));
                    } else if (args[1].equalsIgnoreCase("add")) {
                        BaseTask task = NGBuilder.getTask();
                        if (!(task instanceof BaseTimerTask)) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "実行中のタスクはタイマーが存在しません。");
                            return true;
                        }
                        long t = ((BaseTimerTask) task).getSeconds();
                        ((BaseTimerTask) task).setSecondsRest(t + Long.parseLong(args[2]));
                    }
                } else if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("pause")) {
                        NGBuilder.getTask().pause();
                        sender.sendMessage(NGBuilder.getPrefix() + "タイマーを一時停止しました。");
                    }
                    if (args[1].equalsIgnoreCase("resume")) {
                        NGBuilder.getTask().resume();
                        sender.sendMessage(NGBuilder.getPrefix() + "タイマーを再開しました。");
                    }
                } else {
                    Utility.sendCmdHelp(sender, "/builder timer set <秒数>", "タイマーの秒数を設定します。");
                    Utility.sendCmdHelp(sender, "/builder timer add <秒数>", "タイマーの秒数を追加します。");
                    Utility.sendCmdHelp(sender, "/builder timer pause", "タイマーを一時停止します。");
                    Utility.sendCmdHelp(sender, "/builder timer resume", "タイマーを再開します。");
                    return true;
                }
            }

        } else {
            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "コマンドが存在しないか、許可されていません。");
        }
        return true;
    }

    private void start(CommandSender sender, Command command, String label, String[] args){
        if( args.length == 2 ) {
            if( Long.parseLong(args[1]) <= 0 ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "駄目です。");
                return;
            }
        }
        Bukkit.broadcastMessage(NGBuilder.getPrefix() + "まもなく開始します...");
        long delay = 60;

        GameData.setCycle(GameConfig.Cycle.getInt());
        if( GameData.getCycle() == 1 ){
            delay = 0;
        }
        new BukkitRunnable() {
            @Override
            public void run() {

                if(GameData.getCycle() != 1){
                    Bukkit.broadcastMessage(
                            ChatColor.RED + "======================" +
                                    ChatColor.GREEN + "[" +
                                    ChatColor.AQUA + (GameData.getMaxCycle() - (GameData.getCycle() - 1)) + ChatColor.GRAY + "/" + ChatColor.AQUA + GameData.getMaxCycle()+"巡目" +
                                    ChatColor.GREEN + "]" +
                                    ChatColor.RED + "======================"
                    );
                    for( Player p : Bukkit.getOnlinePlayers() ){
                        p.sendTitle(ChatColor.GREEN + "==== " + (GameData.getMaxCycle() - (GameData.getCycle() - 1)) + "巡目 ====", "" , 10, 40, 10);
                    }
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        long time = GameConfig.RoundTime.getLong();
                        if (args.length == 2) {
                            time = Long.parseLong(args[1]);
                        }
                        BaseTimerTask task = new MainTimerTask(
                                NGBuilder.getMain(),
                                time
                        );
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            PlayerData pd = GamePlayers.getData(p);
                            if (pd.getPlayingType().equals(PlayerData.PlayingType.Player)) {
                                pd.getPlayer().sendMessage(ChatColor.RED + "=====" + ChatColor.WHITE + " ゲームに参加します " + ChatColor.RED + "=====");
                                pd.getPlayer().sendMessage(ChatColor.AQUA + "ひらがなでチャットを送信することで回答できます。");
                                p.setGameMode(GameMode.ADVENTURE);
                                p.setAllowFlight(true);
                                p.setPlayerListName(p.getName() + " ");
                            } else if (pd.getPlayingType().equals(PlayerData.PlayingType.Spectator)) {
                                pd.getPlayer().sendMessage(ChatColor.RED + "=====" + ChatColor.WHITE + " 観戦者として参加しています " + ChatColor.RED + "=====");
                                pd.getPlayer().sendMessage(ChatColor.AQUA + "このセッション中のチャットは観戦者全体にのみ聞こえます。");
                                p.setPlayerListName(ChatColor.GRAY + "" + ChatColor.ITALIC + "[観戦] " + p.getName() + " ");
                                p.setGameMode(GameMode.ADVENTURE);
                                p.setAllowFlight(true);
                            } else if (pd.getPlayingType().equals(PlayerData.PlayingType.GameMaster)) {
                                pd.getPlayer().sendMessage(ChatColor.RED + "=====" + ChatColor.WHITE + " GMとして参加しています " + ChatColor.RED + "=====");
                                pd.getPlayer().sendMessage(ChatColor.YELLOW + "GMのチャットは状況関係なく全体に発信されます。");
                                p.setPlayerListName(ChatColor.YELLOW + "[GM] " + p.getName() + " ");
                            }
                            GamePlayers.setData(pd.getUUID(), pd);
                        }
                        NGBuilder.setTask(task);
                        GameStatus.setStatus(GameStatus.Playing);
                        task.start();
                    }
                }.runTaskLater(NGBuilder.getMain(), 5 * 20);
            }
        }.runTaskLater(NGBuilder.getMain(), delay);
    }

    private void sendCmdHelp(Player sender) {
        Utility.sendCmdHelp(sender, "/builder patchnote", "パッチノートを参照します。");
    }

    private void sendAdminCmdHelp(Player sender) {
        sendCmdHelp(sender);
        Utility.sendCmdHelp(sender, "/builder init", "初期化します。");
        Utility.sendCmdHelp(sender, "/builder start", "ゲームを開始します。");
        Utility.sendCmdHelp(sender, "/builder gmstart", "自分をGMとして参加し、ゲームを開始します。");
        Utility.sendCmdHelp(sender, "/builder stop", "ゲームを強制終了します。");
        Utility.sendCmdHelp(sender, "/builder timer <pause | resume | add | set>", "タイマー関連のコマンドです。");
        Utility.sendCmdHelp(sender, "/builder list", "プレイヤーリストを表示します。");
        Utility.sendCmdHelp(sender, "/builder spec <Player>", "指定プレイヤーの観戦モードを切り替えます。");
        Utility.sendCmdHelp(sender, "/builder loc", "座標設定モードを切り替えます。");
        Utility.sendCmdHelp(sender, "/builder loc <tp | spawn>", "建築者・ワールドスポーンポイントを設定します。");
        Utility.sendCmdHelp(sender, "/builder clean", "建築エリアをきれいにします。");
        Utility.sendCmdHelp(sender, "/builder now", "現在のお題を表示します。");
        Utility.sendCmdHelp(sender, "/builder option <Key> <Value>", "設定を変更します。");
        Utility.sendCmdHelp(sender, "/builder theme <...>", "お題に関するコマンドです。");
        Utility.sendCmdHelp(sender, "/builder perlist", "制約の確率を確認できます。");
        Utility.sendCmdHelp(sender, "/builder reload", "Config他の再読込を行います。");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if( args.length == 1 ){
            if( sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                for (String name : new String[]{
                        "start", "gmstart", "stop", "next", "init", "timer", "option", "spec", "list", "theme", "clean"
                }) {
                    if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                        out.add(name);
                    }
                }
            }
            for (String name : new String[]{
                    "patchnote"
            }) {
                if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                    out.add(name);
                }
            }
        }
        if( args.length >= 2 ){
            if( args[0].equalsIgnoreCase("timer") ){
                for (String name : new String[]{
                        "set", "add", "pause", "resume"
                }) {
                    if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                        out.add(name);
                    }
                }
            }if( args[0].equalsIgnoreCase("loc") ){
                for (String name : new String[]{
                        "spawn", "tp"
                }) {
                    if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                        out.add(name);
                    }
                }
            } else if( args[0].equalsIgnoreCase("spec") ){
                out = new BuilderSpecCmd().onTabComplete(sender, command, alias, args);
            } else if( args[0].equalsIgnoreCase("option") ){
                out = new BuilderOptionCmd().onTabComplete(sender, command, alias, args);
            } else if( args[0].equalsIgnoreCase("theme") ){
                out = new BuilderThemeCmd().onTabComplete(sender, command, alias, args);
            } else if( args[0].equalsIgnoreCase("ng") ){
                out = new BuilderNGCmd().onTabComplete(sender, command, alias, args);
            } else if( args[0].equalsIgnoreCase("debug") ){
                out = new BuilderDebugCmd().onTabComplete(sender, command, alias, args);
            }
        }
        return out;
    }
}
