package io.github.axtuki1.ngbuilder.listener;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.Normalizer;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String str = e.getMessage();
        // マイクラ上で表示がおかしいスペースを半角スペースに変更
        str = Utility.myReplaceAll("ㅤ", " ", str);
        str = Utility.myReplaceAll("　", " ", str);
        e.setMessage(str);
        e.setCancelled(true);
        PlayerData pd = GamePlayers.getData(e.getPlayer().getUniqueId());

        // GM
        if( GamePlayers.isGameMaster(pd) ){
            if (Normalizer.normalize(e.getMessage(), Normalizer.Form.NFKC).toLowerCase().contains("@")) {
                String f = Utility.myReplaceAll("@", "", e.getMessage());
                f = Utility.myReplaceAll("＠", "", f);
                e.setMessage(ChatColor.BOLD + f);
            }
            Bukkit.broadcastMessage(ChatColor.WHITE + "<" + ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.WHITE + "> " + e.getMessage());
            return;
        }
        if( !GameStatus.getStatus().equals(GameStatus.Ready) && !GameStatus.getStatus().equals(GameStatus.End) ){
            // 観戦
            if(GamePlayers.isSpectator(pd)){
                NGBuilder.sendWatcher(ChatColor.WHITE + "[観戦] <" + e.getPlayer().getName() + "> " + e.getMessage());
                return;
            }
        }

        try{
            // プレイヤー
            if(NGBuilder.getTask() != null && GameStatus.getStatus().equals(GameStatus.Playing)){
                NGBuilder.getTask().onChat(e);
                return;
            }
        } catch (Exception e1 ){
            e.setCancelled(true);
            e.getPlayer().sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "チャット処理時に例外が発生しました....");
            Bukkit.broadcast("[例外:" + e1 + "] <" + e.getPlayer().getName() + "> " + e.getMessage(), "Oni.GameMaster");
            Bukkit.broadcast("[例外:" + e1 + "] " + e1.getLocalizedMessage(), "Oni.GameMaster");
            e1.printStackTrace();
        }

        Bukkit.broadcastMessage(ChatColor.WHITE + "<" + e.getPlayer().getName() + "> " + e.getMessage());


    }

}
