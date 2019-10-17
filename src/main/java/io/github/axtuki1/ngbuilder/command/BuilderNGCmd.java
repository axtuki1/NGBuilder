package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.util.Utility;
import io.github.axtuki1.ngbuilder.system.NGData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BuilderNGCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length <= 1 ){
            sendCmdHelp(sender);
            return true;
        }
        if( args[1].equalsIgnoreCase("Priority") ) {

            if( !sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたはこのコマンドを実行できません。");
            }

            if (args.length <= 3) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            try{
                ng = NGData.valueOf(args[2]);
            } catch (IllegalArgumentException e){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGはありません。");
            }

            try{
                HashMap<NGData, Integer> input = GameConfig.NGDataPrioritys.getNGDataPrioritys();
                input.put(ng, Integer.parseInt(args[3]));
                GameConfig.NGDataPrioritys.setNGDataPrioritys(input);
                DecimalFormat df = new DecimalFormat("##0.00%");
                sender.sendMessage(NGBuilder.getPrefix() + "優先度変更: " + ng.getName() + " : " + ng.getPriority() + " / " + df.format(ng.getPriorityPer()));
            } catch ( NumberFormatException e ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値である場所が数値ではありません。");
            }


        }
        if( args[1].equalsIgnoreCase("Bonus") ) {

            if( !sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたはこのコマンドを実行できません。");
            }

            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            if( args[2].equalsIgnoreCase("reset") ){
                try{
                    HashMap<NGData, Double> input = GameConfig.NGDataBonus.getNGDataBonus();
                    for ( NGData ng : NGData.values() ){
                        input.put(ng, ng.getDefaultBonus());
                    }
                    GameConfig.NGDataBonus.setNGDataBonus(input);
                    sender.sendMessage(NGBuilder.getPrefix() + "ボーナスの値をデフォルトに戻しました。" );
                } catch ( NumberFormatException e ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値である場所が数値ではありません。");
                }
                return true;
            }

            if (args.length <= 3) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            try{
                ng = NGData.valueOf(args[2]);
            } catch (IllegalArgumentException e){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGはありません。");
            }

            try{
                HashMap<NGData, Double> input = GameConfig.NGDataBonus.getNGDataBonus();
                input.put(ng, Double.parseDouble(args[3]));
                GameConfig.NGDataBonus.setNGDataBonus(input);
                sender.sendMessage(NGBuilder.getPrefix() + "ボーナス倍率変更: " + ng.getName() + " : " + ng.getBonus() );
            } catch ( NumberFormatException e ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値である場所が数値ではありません。");
            }


        }
        if(args[1].equalsIgnoreCase("info")){
            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            try{
                ng = NGData.valueOf(args[2]);
            } catch (IllegalArgumentException e){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGはありません。");
            }
            sender.sendMessage(ChatColor.RED + "=================================================");
            sender.sendMessage("  " + ng.getName() + ChatColor.GRAY + "  [" + ng.name() + "]");
            sender.sendMessage("  " + ng.getDescription());
            sender.sendMessage(ChatColor.RED + "=================================================");

        }
        if(args[1].equalsIgnoreCase("list")){
            sender.sendMessage(ChatColor.RED + "=================================================");
            HashMap<NGData, Float> perList = NGData.getPriorityList();
            //ソート
            ArrayList<NGData> sortedKeys = new ArrayList(perList.keySet());
            Collections.sort(sortedKeys);
            DecimalFormat df = new DecimalFormat("##0.00%");
            for( NGData item : sortedKeys ){
                ChatColor color = ChatColor.WHITE;
                if( item.getPriority() <= 0 ){
                    color = ChatColor.GRAY;
                }
                String send = color + item.getName() + ChatColor.RESET + ChatColor.GREEN + ": " + ChatColor.YELLOW + "[ "+item.getPriority()+" / " + df.format(perList.get(item))+" | x"+item.getBonus()+" ]";
                if( sender instanceof Player && sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                    TextComponent base = new TextComponent(""), remove = new TextComponent( "[X]" ), reset = new TextComponent( "[R]" );
                    remove.setBold(true);
                    remove.setColor( net.md_5.bungee.api.ChatColor.RED );

                    reset.setBold(true);
                    reset.setColor( net.md_5.bungee.api.ChatColor.YELLOW );

                    remove.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/builder ng Priority "+ item.name() +" 0") );
                    reset.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/builder ng Priority "+ item.name() + " " + item.getDefaultPriority()) );

                    remove.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "/builder ng Priority "+ item.name() +" 0" + "\nクリックで実行します。" ).create() ) );
                    reset.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/builder ng Priority "+ item.name() + " " + item.getDefaultPriority() + "\nクリックで実行します。" ).create() ) );

                    base.addExtra(remove);
                    base.addExtra(reset);
                    base.addExtra(" ");
                    base.addExtra(send);
                    ((Player)sender).spigot().sendMessage(base);
                } else {
                    sender.sendMessage(send);
                }
                //生の値↓
//                sender.sendMessage(item.getName() + ChatColor.RESET + ChatColor.GREEN + ": " + ChatColor.YELLOW + perList.get(item));
            }
            sender.sendMessage( ChatColor.YELLOW + "合計: " + ChatColor.GREEN + sortedKeys.size() + ChatColor.YELLOW + "件" );
            sender.sendMessage(ChatColor.RED + "=================================================");
        }
        return true;
    }

    private void sendCmdHelp(CommandSender sender) {
        if (sender.hasPermission(NGBuilder.getGameMasterPermission())) {
            Utility.sendCmdHelp(sender, "/builder ng Priority <NG> <優先度>", "優先度を設定します。");
            Utility.sendCmdHelp(sender, "/builder ng Bonus <NG> <倍率>", "ボーナス倍率を設定します。");
        }
        Utility.sendCmdHelp(sender, "/builder ng list", "NGのリストと出現率が参照できます。");
        Utility.sendCmdHelp(sender, "/builder ng info <NG>", "NGの説明が参照できます。");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        // b t a o a
        if( args.length == 2 ){
            if(sender.hasPermission(NGBuilder.getGameMasterPermission())){
                for (String name : new String[]{
                        "Priority", "Bonus"
                }) {
                    if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                        out.add(name);
                    }
                }
            }
            for (String name : new String[]{
                    "list", "info"
            }) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    out.add(name);
                }
            }
        } else if( args.length == 3 ){
            if( args[1].equalsIgnoreCase("Priority") ||args[1].equalsIgnoreCase("Bonus") || args[1].equalsIgnoreCase("info") ){
                if( (args[1].equalsIgnoreCase("Priority")||args[1].equalsIgnoreCase("Bonus")) && !sender.hasPermission(NGBuilder.getGameMasterPermission() )) return out;
                for (NGData ng : NGData.values()) {
                    if (ng.name().toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(ng.name());
                    }
                }
            }
        }
        return out;
    }
}
