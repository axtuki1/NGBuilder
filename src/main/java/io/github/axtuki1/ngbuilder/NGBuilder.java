package io.github.axtuki1.ngbuilder;

import io.github.axtuki1.ngbuilder.command.BuilderCommand;
import io.github.axtuki1.ngbuilder.command.CleanCommand;
import io.github.axtuki1.ngbuilder.listener.*;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.system.GameData;
import io.github.axtuki1.ngbuilder.task.BaseTask;
import io.github.axtuki1.ngbuilder.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public final class NGBuilder extends JavaPlugin {

    private static BaseTask timerTask = null;
    private HashMap<String, TabExecutor> commands;
    private static NGBuilder main;
    private static World world;

    public static String[] getChangeLog(){
        return new String[]{
                "1.14.4仮対応。新ブロックやID変更に追われて対応漏れアリ。"
        };
    }

    @Override
    public void onEnable() {
        main = this;

        saveDefaultConfig();
        reloadConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                world = Bukkit.getWorlds().get(0);
                init();
//                GamePlayers.init();
            }
        }.runTaskAsynchronously(this);

        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

        commands = new HashMap<>();

        commands.put("builder", new BuilderCommand());
        commands.put("clean", new CleanCommand());

    }

    public static void init() {
        NGBuilder.getMain().reloadConfig();
        GameStatus.setStatus(GameStatus.Ready);
        GamePlayers.init();
        GameData.init();
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
        if (NGBuilder.getTask() != null) {
            NGBuilder.getTask().stop();
            NGBuilder.setTask(null);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setPlayerListName(p.getName() + " ");
            p.setFoodLevel(20);
            p.setCollidable(false);
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
            p.setBedSpawnLocation(getWorld().getSpawnLocation(), true);
            p.setHealth(20);
            p.setSaturation(0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                }
            }.runTask(getMain());
            if (p.hasPermission("Builder.GameMaster")) {
                p.setPlayerListName(ChatColor.YELLOW + "[GM] " + p.getName() + " ");
//                JinroPlayers.addPlayer(p.getUniqueId(), new PlayerData(p.getUniqueId(), PlayerData.PlayingType.GameMaster));
            }
            if( !p.hasPermission("Builder.GameMaster") ){
                p.getInventory().clear();
                p.teleport(getWorld().getSpawnLocation().add(0.5, 0, 0.5));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.setAllowFlight(false);
                    }
                }.runTask(getMain());
            }
        }
    }

    @Override
    public void onDisable() {
        if (NGBuilder.getTask() != null) {
            NGBuilder.getTask().stop();
            NGBuilder.setTask(null);
        }
    }

    public static NGBuilder getMain() {
        return main;
    }

    public static String getPrefix(){
        return ChatColor.GREEN + "[" + ChatColor.AQUA + "NGBuilder" + ChatColor.GREEN + "] " + ChatColor.WHITE;
    }

    public static String getGameMasterPermission() {
        return "Builder.GameMaster";
    }

    public static String getBroadcasterPermission(){
        return "Builder.Broadcaster";
    }

    public static World getWorld() {
        return world;
    }

    public static BaseTask getTask() {
        return timerTask;
    }

    public static void setTask(BaseTask task) {
        NGBuilder.timerTask = task;
    }

    public static void sendWatcher(String s, boolean isLogging) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (GamePlayers.isGameMaster(p) || GamePlayers.isSpectator(p)) {
                p.sendMessage(s);
            }
        }
        if (isLogging) {
            sendConsole(s);
        }
    }

    public static void sendWatcher(String s) {
        sendWatcher(s, true);
    }

    public static void sendGameMaster(String s, boolean isLogging) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (GamePlayers.isGameMaster(p)) {
                p.sendMessage(s);
            }
        }
        if (isLogging) {
            sendConsole(s);
        }
    }

    public static void sendConsole(String s) {
        getMain().getServer().getConsoleSender().sendMessage(s);
    }

    public static void sendGameMaster(String s) {
        sendGameMaster(s, true);
    }

    public static void sendSpectator(String s, boolean isLogging) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (GamePlayers.isSpectator(p)) {
                p.sendMessage(s);
            }
        }
        if (isLogging) {
            sendConsole(s);
        }
    }

    public static void sendSpectator(String s) {
        sendSpectator(s, true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commands.get(command.getName()).onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return commands.get(command.getName()).onTabComplete(sender, command, alias, args);
    }

    public static void sendDebug(String s){
        if( getMain().getConfig().getBoolean("Debug") ){
            Bukkit.broadcast(ChatColor.GREEN + "[NGB:DEBUG] " + s, getGameMasterPermission());
        }
    }


}
