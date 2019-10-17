package io.github.axtuki1.ngbuilder.listener;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.util.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        // 参加時
        GameConfig.WorldSpawnPoint.reload();
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.AQUA + NGBuilder.getMain().getDescription().getName() + ChatColor.GRAY + " v" + ChatColor.YELLOW + NGBuilder.getMain().getDescription().getVersion() + ChatColor.WHITE + " ChangeLog:");
        p.sendMessage(NGBuilder.getChangeLog());
        p.setCollidable(false);
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard() );
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(p.getUniqueId(), true);
            p.setPlayerListName(p.getName() + " ");
            if( pd == null ) {
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
//                p.setFlying(true);
                p.setScoreboard( Bukkit.getScoreboardManager().getNewScoreboard() );
                pd = new PlayerData(p.getUniqueId(), PlayerData.PlayingType.Spectator);
                p.sendMessage(ChatColor.GREEN + "====== 現在観戦モードです ======");
                GamePlayers.setData(p.getUniqueId(), pd);
                p.setPlayerListName(ChatColor.WHITE + "[観戦] " + p.getName() + " ");
                e.setJoinMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + Utility.myReplaceAll(ChatColor.YELLOW.toString(), "", e.getJoinMessage()));
                p.teleport(GameConfig.WorldSpawnPoint.getLocation());
            } else if( !pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                p.setPlayerListName(pd.getName() + " ");
                if( pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) ){
                    p.setGameMode(GameMode.ADVENTURE);
                    p.setAllowFlight(true);
                    p.setScoreboard( Bukkit.getScoreboardManager().getNewScoreboard() );
                    p.setPlayerListName(ChatColor.WHITE + "[観戦] " + p.getName() + " ");
                    p.sendMessage(ChatColor.GREEN + "====== 現在観戦モードです ======");
                    e.setJoinMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + Utility.myReplaceAll(ChatColor.YELLOW.toString(), "", e.getJoinMessage()));
                }
                p.teleport(GameConfig.WorldSpawnPoint.getLocation());
            } else if(pd.getPlayingType().equals(PlayerData.PlayingType.Player)){
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setPlayerListName(pd.getName() + " ");
                p.teleport(GameConfig.WorldSpawnPoint.getLocation());
            }
        } else {
            p.teleport(GameConfig.WorldSpawnPoint.getLocation());
        }
    }

}
