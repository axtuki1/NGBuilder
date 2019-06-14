package io.github.axtuki1.ngbuilder.listener;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        // 参加時
        GameConfig.WorldSpawnPoint.reload();
        Player p = e.getPlayer();
        p.setCollidable(false);
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard() );
        if( GameStatus.getStatus().equals(GameStatus.Playing) ){
            PlayerData pd = GamePlayers.getData(p.getUniqueId(), true);
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
                p.teleport(GameConfig.WorldSpawnPoint.getLocation());
            }
        } else {
            p.teleport(GameConfig.WorldSpawnPoint.getLocation());
        }
    }

}
