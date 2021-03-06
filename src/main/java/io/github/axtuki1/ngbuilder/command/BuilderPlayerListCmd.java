package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BuilderPlayerListCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Player> unassignedPlayers = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            PlayerData pd = GamePlayers.getData(p, true);
            if(GameStatus.getStatus().equals(GameStatus.Ready)){
                if( pd == null ){
                    unassignedPlayers.add(p);
                }
            }
        }
        List<PlayerData> joinedPlayers = new ArrayList<>();
        for(PlayerData pd : GamePlayers.getPlayers().values()){
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                if( pd.getPlayer() != null ){
                    joinedPlayers.add(pd);
                }
            }
        }
        List<PlayerData> spectatorPlayers = new ArrayList<>();
        for(PlayerData pd : GamePlayers.getPlayers().values()){
            if( pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) ){
                if( pd.getPlayer() != null ){
                    spectatorPlayers.add(pd);
                }
            }
        }
        sender.sendMessage( ChatColor.RED + "======================================");
        if( unassignedPlayers.size() != 0 ){
            sender.sendMessage( ChatColor.GOLD + "[未割り当てプレイヤー]");
            StringBuilder unassigned_sb = new StringBuilder();
            String unassigned_output = "";
            for( Player p : unassignedPlayers ){
                unassigned_sb.append(p.getName()).append(", ");
            }
            if( (unassigned_sb.length() - 2) >= 0 ){
                unassigned_output = unassigned_sb.substring(0, unassigned_sb.length() - 2);
            } else {
                unassigned_output = unassigned_sb.toString();
            }
            sender.sendMessage( unassigned_output );
            sender.sendMessage( ChatColor.RED + "======================================");
        }
        if( spectatorPlayers.size() != 0 ){
            sender.sendMessage( ChatColor.GOLD + "[観戦プレイヤー]");
            StringBuilder spectator_sb = new StringBuilder();
            String spectator_output = "";
            for( PlayerData playerData : spectatorPlayers ){
                spectator_sb.append(playerData.getName()).append(", ");
            }
            if( (spectator_sb.length() - 2) >= 0 ){
                spectator_output = spectator_sb.substring(0, spectator_sb.length() - 2);
            } else {
                spectator_output = spectator_sb.toString();
            }
            sender.sendMessage( spectator_output );
            sender.sendMessage( ChatColor.RED + "======================================");
        }
        HashMap<UUID, PlayerData> pls = GamePlayers.getPlayers();
        StringBuilder senderPlayingList = new StringBuilder();
        if(pls.size() != 0){
            sender.sendMessage( ChatColor.GOLD + "[参加プレイヤー]");
            for( PlayerData pd : pls.values() ){
                String addText = "";
                if( pd.getPlayingType().equals(PlayerData.PlayingType.Player) ){
                    senderPlayingList.append(pd.getName());
                }
                if( GamePlayers.getBuiltPlayers().contains(pd.getUUID()) ){
                    senderPlayingList.append(ChatColor.GRAY).append("[built]");
                }
                senderPlayingList.append(ChatColor.GRAY).append(", ").append(ChatColor.WHITE);
            }
            String out = "";
            if( (senderPlayingList.length() - 2) >= 0 ){
                out = senderPlayingList.substring(0, senderPlayingList.length() - 4);
            } else {
                out = senderPlayingList.toString();
            }
            sender.sendMessage( out );
            sender.sendMessage( ChatColor.RED + "======================================");
        }
        sender.sendMessage( ChatColor.AQUA + "サーバー人数: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + "人 "
                + ChatColor.GREEN + "参加人数: " + ChatColor.YELLOW + joinedPlayers.size() + "人 "
                + ChatColor.GREEN + "観戦人数: "+ ChatColor.YELLOW + spectatorPlayers.size() + "人" );
        if( unassignedPlayers.size() != 0 ){
            sender.sendMessage( ChatColor.RED + "未割り当て人数(GM含む): " + ChatColor.YELLOW + unassignedPlayers.size() + "人" );
        }
        if( joinedPlayers.size() <= 2 ){
            sender.sendMessage( ChatColor.RED + "おすすめはしない人数です...");
        } else {
            sender.sendMessage( ChatColor.GOLD + "/builder start でゲームを開始できます。");
        }
        sender.sendMessage( ChatColor.RED + "======================================");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }


}
