package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.GameData;
import io.github.axtuki1.ngbuilder.system.NGData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuilderTeamCmd implements TabExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 1 ){
        } else if( args[1].equalsIgnoreCase("set") ){
            if( args.length <= 2 ){

            } else {
                Player p = Bukkit.getPlayerExact(args[2]);
                if( p == null ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "指定プレイヤーはオフラインです。");
                    return true;
                }
                PlayerData pd = GamePlayers.getData(p);
                if(
                        pd.getPlayingType().equals(PlayerData.PlayingType.Player) ||
                        (pd.getPlayingType().equals(PlayerData.PlayingType.Spectator) && args.length >= 5 && args[4].equalsIgnoreCase("force"))
                ){
                    try {
                        pd.setPlayingType(PlayerData.PlayingType.Player);
                        ChatColor color = ChatColor.valueOf(args[3].toUpperCase());
                        pd.setColor(color);
                        GamePlayers.setData(pd.getUUID(), pd);
                        pd.getPlayer().sendMessage(NGBuilder.getPrefix() + "あなたは " + pd.getColorName() + ChatColor.WHITE + " チームになりました。");
                        pd.getPlayer().setPlayerListName(pd.getName() + " ");
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "指定した色は存在しません。");
                    }
                } else {
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "指定プレイヤーは観戦者です。forceをコマンドの後ろにつけると参加者にします。");
                    return true;
                }
            }
        } else if( args[1].equalsIgnoreCase("rest") ){
            if( args.length <= 2 ){
                List<ChatColor> color = new ArrayList<>();
                int size = 0;
                for( PlayerData pd : GamePlayers.getPlayersFromPlayingType(PlayerData.PlayingType.Player) ){
                    if( pd.getColor().equals(ChatColor.WHITE) ){
                        size++;
                    }
                }
                float half = size / 2;
                if( half <= 10 ){
                    // 2チーム
                    color.add(ChatColor.RED);
                    color.add(ChatColor.AQUA);
                } else {
                    // 4チーム
                    color.add(ChatColor.RED);
                    color.add(ChatColor.AQUA);
                    color.add(ChatColor.YELLOW);
                    color.add(ChatColor.GREEN);
                }
                int i = 0;
                List<Player> all = new ArrayList<>();
                all.addAll(Bukkit.getOnlinePlayers());
                Collections.shuffle(all);
                for (Player p : all) {
                    PlayerData pd = GamePlayers.getData(p);
                    if (pd.getPlayingType().equals(PlayerData.PlayingType.Player)) {
                        if( pd.getColor().equals(ChatColor.WHITE) ){
                            pd.setColor( color.get(i) );
                            i++;
                            if( i == color.size() ){
                                i = 0;
                            }
                        }
                        pd.getPlayer().sendMessage(NGBuilder.getPrefix() + "あなたは " + pd.getColorName() + ChatColor.WHITE + " チームになりました。");
                        pd.getPlayer().setPlayerListName(pd.getName() + " ");
                    }
                    GamePlayers.setData(pd.getUUID(), pd);
                }

            } else {
                for( Player p : Bukkit.getOnlinePlayers() ){
                    PlayerData pd = GamePlayers.getData(p);
                    try {
                        pd.setPlayingType(PlayerData.PlayingType.Player);
                        ChatColor color = ChatColor.valueOf(args[2].toUpperCase());
                        pd.setColor(color);
                        GamePlayers.setData(pd.getUUID(), pd);
                        pd.getPlayer().sendMessage(NGBuilder.getPrefix() + "あなたは " + pd.getColorName() + ChatColor.WHITE + " チームになりました。");
                        pd.getPlayer().setPlayerListName(pd.getName() + " ");
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "指定した色は存在しません。");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if( args.length == 2 ){
            for (String name : new String[]{
                    "set", "rest"
            }) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    out.add(name);
                }
            }
        }
        if( args.length == 3 ){
            if( args[1].equalsIgnoreCase("set") ){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(p.getName());
                    }
                }
            }
            if( args[1].equalsIgnoreCase("rest") ){
                for (ChatColor c : ChatColor.values()) {
                    if (c.name().toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(c.name());
                    }
                }
            }
        }
        if( args.length == 4 ){
            if( args[1].equalsIgnoreCase("set") ){
                for (ChatColor c : ChatColor.values()) {
                    if (c.name().toLowerCase().startsWith(args[3].toLowerCase())) {
                        out.add(c.name());
                    }
                }
            }
        }
        return out;
    }
}
