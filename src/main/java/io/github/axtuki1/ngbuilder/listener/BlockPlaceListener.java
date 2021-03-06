package io.github.axtuki1.ngbuilder.listener;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.util.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.BlockData;
import io.github.axtuki1.ngbuilder.system.NGData;
import io.github.axtuki1.ngbuilder.task.MainTimerTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent e) {
        if (GameStatus.getStatus().equals(GameStatus.Ready)) {
            if (GamePlayers.getSettingPlayers().contains(e.getPlayer().getUniqueId())) {
                if (e.getItemInHand() != null) {
                    ItemStack item = e.getItemInHand();
                    String dis = item.getItemMeta().getDisplayName();
                    if (item.getType().equals(Material.PURPLE_WOOL) && dis.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "建築可能エリア Point1")) {
                        GameConfig.canBuilderPlacePoint1.setLocation(e.getBlock().getLocation());
                        e.setCancelled(true);
                        item.setType(Material.AIR);
                        e.getPlayer().getInventory().setItemInMainHand(item);
                        e.getPlayer().sendMessage(NGBuilder.getPrefix() + "建築可能エリアのPoint1を設定しました。");
                        if(GameConfig.canBuilderPlacePoint1.getLocation() != null && GameConfig.canBuilderPlacePoint1.getLocation() != null){
                            int count = 0;

                            Location point1 = GameConfig.canBuilderPlacePoint1.getLocation(),
                                    point2 = GameConfig.canBuilderPlacePoint2.getLocation();
                            for (int xPoint = Math.min(point1.getBlockX(), point2.getBlockX()); xPoint <=
                                    Math.max(point1.getBlockX(), point2.getBlockX())
                                    ; xPoint++) {
                                for (int yPoint = Math.min(point1.getBlockY(), point2.getBlockY()); yPoint <=
                                        Math.max(point1.getBlockY(), point2.getBlockY())
                                        ; yPoint++) {
                                    for (int zPoint = Math.min(point1.getBlockZ(), point2.getBlockZ()); zPoint <=
                                            Math.max(point1.getBlockZ(), point2.getBlockZ())
                                            ; zPoint++) {
                                        Location loc = new Location(point1.getWorld() ,xPoint, yPoint, zPoint);
                                        count++;
                                    }
                                }
                            }
                            e.getPlayer().sendMessage(NGBuilder.getPrefix() + "建築可能範囲: "+count+"ブロック");
                        }
                    } else if (item.getType().equals(Material.LIGHT_BLUE_WOOL) && dis.equalsIgnoreCase(ChatColor.AQUA + "建築可能エリア Point2")) {
                        GameConfig.canBuilderPlacePoint2.setLocation(e.getBlock().getLocation());
                        e.setCancelled(true);
                        item.setType(Material.AIR);
                        e.getPlayer().getInventory().setItemInMainHand(item);
                        e.getPlayer().sendMessage(NGBuilder.getPrefix() + "建築可能エリアのPoint2を設定しました。");
                        if(GameConfig.canBuilderPlacePoint1.getLocation() != null && GameConfig.canBuilderPlacePoint1.getLocation() != null){
                            int count = 0;

                            Location point1 = GameConfig.canBuilderPlacePoint1.getLocation(),
                                    point2 = GameConfig.canBuilderPlacePoint2.getLocation();
                            for (int xPoint = Math.min(point1.getBlockX(), point2.getBlockX()); xPoint <=
                                    Math.max(point1.getBlockX(), point2.getBlockX())
                                    ; xPoint++) {
                                for (int yPoint = Math.min(point1.getBlockY(), point2.getBlockY()); yPoint <=
                                        Math.max(point1.getBlockY(), point2.getBlockY())
                                        ; yPoint++) {
                                    for (int zPoint = Math.min(point1.getBlockZ(), point2.getBlockZ()); zPoint <=
                                            Math.max(point1.getBlockZ(), point2.getBlockZ())
                                            ; zPoint++) {
                                        Location loc = new Location(point1.getWorld() ,xPoint, yPoint, zPoint);
                                        count++;
                                    }
                                }
                            }
                            e.getPlayer().sendMessage(NGBuilder.getPrefix() + "建築可能範囲: "+count+"ブロック");
                        }
                    } else if (item.getType().equals(Material.YELLOW_WOOL) && dis.equalsIgnoreCase(ChatColor.GOLD + "建築者TPポイント")) {
                        GameConfig.BuilderSpawnPoint.setLocation(e.getBlock().getLocation());
                        e.setCancelled(true);
                        item.setType(Material.AIR);
                        e.getPlayer().getInventory().setItemInMainHand(item);
                        e.getPlayer().sendMessage(NGBuilder.getPrefix() + "建築者がTPする座標を設定しました。");
                    }
                }
            }
        }
        if (GamePlayers.getDebuggingPlayers().contains(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage("Block: " + e.getBlock().getType() + ":" + e.getBlock().getData());
            e.getPlayer().sendMessage("BlockPlaced: " + e.getBlockPlaced().getType() + ":" + e.getBlockPlaced().getData());
            NGData ng = GamePlayers.getCurrentDebuggingNGData();
            if (ng != null) {
                BlockData bd = ng.getList().stream().filter(bda -> bda.getMaterial().equals(e.getBlock().getType())).findAny().orElse(null);
                e.getPlayer().sendMessage(ng.name() + ": " + ng.getName());
                e.getPlayer().sendMessage(" canUse() -> " + (ng.canUse(e.getBlock())?"置ける":"置けない"));
                e.getPlayer().sendMessage(
                        " ちなみに表のBlockDataのMaterialにある？ -> " +
                                (
                                        bd != null ?
                                                "ある":
                                                "ない"
                                )
                );
                e.getPlayer().sendMessage(
                        " List BlockData -> " +
                                (
                                        bd != null ?
                                                "Material: "+bd.getMaterial()+", data: " + bd.getDataValue() + ", isNon: " +bd.isNonDataValue() :
                                                "は？"
                                )
                );
                bd = new BlockData(e.getBlock());
                e.getPlayer().sendMessage(
                        " Place BlockData -> " +
                                (
                                        bd != null ?
                                                "Material: "+bd.getMaterial()+", data: " + bd.getDataValue() + ", isNon: " +bd.isNonDataValue() :
                                                "は？"
                                )
                );
            }
        }
        if (GameStatus.getStatus().equals(GameStatus.Playing)) {
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if (pd.getPlayingType().equals(PlayerData.PlayingType.Player)) {
                if (pd.isBuilder()) {
                    if (!Utility.isLocationArea(
                            e.getBlock().getLocation(),
                            GameConfig.canBuilderPlacePoint1.getLocation(),
                            GameConfig.canBuilderPlacePoint2.getLocation()
                    )) {
                        e.setCancelled(true);
                        e.setBuild(false);
                        p.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "この場所は建築可能エリア外です。");
                        return;
                    }
                    if (NGBuilder.getTask() instanceof MainTimerTask) {
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        if (!((MainTimerTask) NGBuilder.getTask()).isEndProcessing()) {
                            if (!task.getCurrentNGData().canUse(e.getBlock())) {
                                NGBuilder.sendConsole(e.getBlock().getType() + ":" + e.getBlock().getData());
                                task.NGEnd();
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder()){
                    if( !Utility.isLocationArea(
                            e.getBlock().getLocation(),
                            GameConfig.canBuilderPlacePoint1.getLocation(),
                            GameConfig.canBuilderPlacePoint2.getLocation()
                    ) ){
                        e.setCancelled(true);
                        p.sendMessage( NGBuilder.getPrefix() + ChatColor.RED + "この場所は建築可能エリア外です。" );
                        return;
                    } else {
                        if( NGBuilder.getTask() instanceof MainTimerTask ){
                            MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                            if( !((MainTimerTask)NGBuilder.getTask()).isEndProcessing() ){
                                if( task.getCurrentNGData().getNGMode().equals(NGData.NGMode.BlockBreakDeny) ){
                                    task.NGEnd();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFly(PlayerToggleFlightEvent e){
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder()) {
                    if (NGBuilder.getTask() instanceof MainTimerTask) {
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        if (!((MainTimerTask) NGBuilder.getTask()).isEndProcessing()) {
                            if (e.isFlying() && task.getCurrentNGData().getNGMode().equals(NGData.NGMode.FlyingDeny)) {
                                task.NGEnd();
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBlock(EntityExplodeEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent e){
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder()) {
                    if (NGBuilder.getTask() instanceof MainTimerTask) {
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        if (!((MainTimerTask) NGBuilder.getTask()).isEndProcessing()) {
                            if (task.getCurrentNGData().getNGMode().equals(NGData.NGMode.LiquidDeny)) {
                                task.NGEnd();
                            } else if( task.getCurrentNGData().getNGMode().equals(NGData.NGMode.Only) ){
                                if( !task.getCurrentNGData().canUse(new BlockData(e.getBucket())) ){
                                    NGBuilder.sendConsole(e.getBucket()+"");
                                    task.NGEnd();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder()) {
                    if (NGBuilder.getTask() instanceof MainTimerTask) {
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        if (!((MainTimerTask) NGBuilder.getTask()).isEndProcessing()) {
                            if (task.getCurrentNGData().getNGMode().equals(NGData.NGMode.EntityDeny)) {
                                if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                                    if( e.getMaterial().toString().contains("SPAWN_EGG") ||
                                            e.getMaterial().equals(Material.END_CRYSTAL) ||
                                            e.getMaterial().toString().contains("MINECART") ||
                                            e.getMaterial().toString().contains("BOAT") ){
                                        task.NGEnd();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent e) {
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(e.getPlayer());
            Player p = e.getPlayer();
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.isBuilder()) {
                    if (NGBuilder.getTask() instanceof MainTimerTask) {
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        if (!((MainTimerTask) NGBuilder.getTask()).isEndProcessing()) {
                            if (task.getCurrentNGData().getNGMode().equals(NGData.NGMode.EntityDeny)) {
                                task.NGEnd();
                            }
                        }
                    }
                }
            }
        }
    }

}
