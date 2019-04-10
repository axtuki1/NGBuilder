package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuilderSpecCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length <= 1 ){
            sendCmdHelp((Player)sender);
            return true;
        }
        Player p = Bukkit.getPlayerExact( args[1] );
        if( p == null ){
            sender.sendMessage( NGBuilder.getPrefix() + ChatColor.RED + "指定プレイヤーはオフラインです。" );
            return true;
        }
        PlayerData pd = GamePlayers.getData(p.getUniqueId());
        if( !pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) ){
            pd.setPlayingType(PlayerData.PlayingType.Spectator);
            sender.sendMessage( NGBuilder.getPrefix() + ChatColor.GREEN + p.getName() + "を観戦者に設定しました。" );
            p.sendMessage(NGBuilder.getPrefix() + ChatColor.YELLOW + "あなたは観戦者に設定されました。" );
        } else {
            pd.setPlayingType(PlayerData.PlayingType.Player);
            sender.sendMessage( NGBuilder.getPrefix() + ChatColor.GREEN + p.getName() + "を参加者に設定しました。" );
            p.sendMessage(NGBuilder.getPrefix() + ChatColor.YELLOW + "あなたは参加者に設定されました。" );
        }
        GamePlayers.setData(p.getUniqueId(), pd);
        return true;
    }

    private void sendCmdHelp(Player sender) {
        Utility.sendCmdHelp(sender, "/oni spec <Player>", "指定プレイヤーの観戦モードを切り替えます。(自動役配布の対象外)");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if( args.length == 2 ){
            for( Player p : Bukkit.getOnlinePlayers() ){
                if ( p.getName().toLowerCase().startsWith(args[1].toLowerCase()) ) {
                    out.add(p.getName());
                }
            }
        }
        return out;
    }

}
