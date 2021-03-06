package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
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
import java.util.HashMap;
import java.util.List;

public class BuilderNGCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length <= 1 ){
            sendCmdHelp(sender);
            return true;
        }
        if( args[1].equalsIgnoreCase("edit") ) {

            if( !sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたはこのコマンドを実行できません。");
            }


            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            ng = new NGData(args[2]);


            if(!ng.isNotFound()){
                if( args.length >= 4 && args[3].equalsIgnoreCase("registerMode") ){
                    Player p = ((Player)sender);
                    PlayerData pd = GamePlayers.getData(p);
                    if( pd.isBlockRegisterMode() ){
                        sender.sendMessage( NGBuilder.getPrefix() + "ブロックの登録モードを終了しました。" );
                    } else {
                        sender.sendMessage( NGBuilder.getPrefix() + "ブロックの登録モードに入りました。" );
                    }
                    pd.setNGData(ng);
                    pd.setBlockRegisterMode(!pd.isBlockRegisterMode());
                    GamePlayers.setData(pd.getUUID(), pd);
                } else if( args.length <= 4 ) {
                    sender.sendMessage(ChatColor.RED + "=================================================");
                    sender.sendMessage("  " + ng.getName() + ChatColor.GRAY + "  [" + ng.getId() + "]");
                    sender.sendMessage("  " + (ng.getDescription().equals("") ? "[紹介文は設定されていません]" : ng.getDescription()));
                    sender.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
                    sender.sendMessage("  " + ChatColor.WHITE + "動作モード: "+ ng.getNGMode());
                    sender.sendMessage("  " + ChatColor.WHITE + "カウント判定: "+ ng.getCountDenyMode());
                    sender.sendMessage("  " + ChatColor.WHITE + "出現率: "+ ng.getPriority());
                    sender.sendMessage("  " + ChatColor.WHITE + "ボーナス: +"+ ng.getBonusPoint());
                    sender.sendMessage("  " + ChatColor.WHITE + "減点: "+ ng.getPenalty());
                    sender.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
                    sender.sendMessage("  " +ChatColor.WHITE + "name: 名称を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "shortName: サイドバーなどに使用される短縮された名称を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "startDesc: 建築時に表示される紹介文を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "desc: 完全な紹介文を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "mode: NGの動作モードを変更します 以下の選択肢があります");
                    sender.sendMessage("  " +ChatColor.WHITE + "      Only, Deny, BlockBreakDeny, FlyingDeny");
                    sender.sendMessage("  " +ChatColor.WHITE + "      LiquidDeny, EntityDeny, CountDeny, StopTimeDeny");
                    sender.sendMessage("  " +ChatColor.WHITE + "priority: 出現率を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "bonus: ボーナスポイントを変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "penalty: 建築者失敗時の減点を変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "countMode: ブロックカウントモード時の判定の厳しさを変更します");
                    sender.sendMessage("  " +ChatColor.WHITE + "           選択肢: Normal, Hard, Hardcore");
                    sender.sendMessage("  " +ChatColor.WHITE + "           ※カウントモード以外では使用されません");
                    sender.sendMessage("  " +ChatColor.WHITE + "registerMode: ブロックの登録を行うモードを切り替えます");
                    sender.sendMessage(ChatColor.RED + "=================================================");
                } else {
                    if( args[3].equalsIgnoreCase("name") ){
                        ng.setName(Utility.CommandText(args, 4));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "名称変更: "+ ng.getId()+ ": " + ng.getName());
                    } else if( args[3].equalsIgnoreCase("shortName") ){
                        ng.setShortName(Utility.CommandText(args, 4));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "短縮名称変更: "+ ng.getId()+ ": " + ng.getShortName());
                    } else if( args[3].equalsIgnoreCase("desc") ){
                        ng.setDescription(Utility.CommandText(args, 4));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "紹介文変更: "+ ng.getId()+ ": " + ng.getDescription());
                    } else if( args[3].equalsIgnoreCase("startDesc") ){
                        ng.setStartDescription(Utility.CommandText(args, 4));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "開始時紹介文変更: "+ ng.getId()+ ": " + ng.getStartDescription());
                    } else if( args[3].equalsIgnoreCase("priority") ){
                        try{
                            int i = Integer.parseInt(args[4]);
                            ng.setPriority(i);
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "出現率変更: "+ ng.getId()+ ": " + ng.getPriority());
                        } catch ( NumberFormatException e) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で入力してください。");
                        }
                    } else if( args[3].equalsIgnoreCase("penalty") ){
                        try{
                            int i = Integer.parseInt(args[4]);
                            ng.setPenalty(i);
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "建築者減点変更: "+ ng.getId()+ ": " + ng.getPenalty());
                        } catch ( NumberFormatException e) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で入力してください。");
                        }
                    } else if( args[3].equalsIgnoreCase("bonus") ){
                        try{
                            int i = Integer.parseInt(args[4]);
                            ng.setBonusPoint(i);
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "ボーナス変更: "+ ng.getId()+ ": " + ng.getBonusPoint());
                        } catch ( NumberFormatException e) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で入力してください。");
                        }
                    } else if( args[3].equalsIgnoreCase("mode") ){
                        try{
                            NGData.NGMode mode = NGData.NGMode.valueOf(args[4]);
                            ng.setNGMode(mode);
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "モード変更: "+ ng.getId()+ ": " + ng.getNGMode());
                        } catch ( IllegalArgumentException e) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "正しく入力してください。");
                        }
                    } else if( args[3].equalsIgnoreCase("countmode") ){
                        try{
                            NGData.CountDenyMode mode = NGData.CountDenyMode.valueOf(args[4]);
                            ng.setCountDenyMode(mode);
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "カウントモード変更: "+ ng.getId()+ ": " + ng.getCountDenyMode());
                        } catch ( IllegalArgumentException e) {
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "正しく入力してください。");
                        }
                    }
                    ng.save();
                }
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGは存在しません。");
            }

        }
        if(args[1].equalsIgnoreCase("create")){
            if( !sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたはこのコマンドを実行できません。");
            }

            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            ng = new NGData(args[2]);

            if(ng.isNotFound()){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "新しいNG[ "+ng.getId()+" ]を作成しました。");
                ng.save();
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGは存在します。");
            }
        }

        if(args[1].equalsIgnoreCase("remove")){
            if( !sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたはこのコマンドを実行できません。");
            }

            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            ng = new NGData(args[2]);

            if(!ng.isNotFound()){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "NG[ "+ng.getId()+" ]を削除しました。");
                ng.remove();
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGは存在しません。");
            }
        }

        if(args[1].equalsIgnoreCase("info")){
            if (args.length <= 2) {
                sendCmdHelp(sender);
                return true;
            }

            NGData ng = null;

            try{
                ng = new NGData(args[2]);
            } catch (IllegalArgumentException e){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "そのNGはありません。");
            }
            sender.sendMessage(ChatColor.RED + "=================================================");
            sender.sendMessage("  " + ng.getName() + ChatColor.GRAY + "  [" + ng.getId() + "]");
            sender.sendMessage("  " + (ng.getDescription().equals("") ? "[紹介文は設定されていません]" : ng.getDescription()));
            sender.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
            sender.sendMessage("  " + ChatColor.WHITE + "動作モード: "+ ng.getNGMode());
            sender.sendMessage("  " + ChatColor.WHITE + "カウント判定: "+ ng.getCountDenyMode());
            sender.sendMessage("  " + ChatColor.WHITE + "出現率: "+ ng.getPriority());
            sender.sendMessage("  " + ChatColor.WHITE + "ボーナス: +"+ ng.getBonusPoint());
            sender.sendMessage("  " + ChatColor.WHITE + "減点: "+ ng.getPenalty());
            sender.sendMessage(ChatColor.RED + "=================================================");
        }
        if(args[1].equalsIgnoreCase("list")){
            sender.sendMessage(ChatColor.RED + "=================================================");
            HashMap<NGData, Float> perList = NGData.getPriorityList();
            //ソート
            ArrayList<NGData> sortedKeys = new ArrayList(perList.keySet());
//            Collections.sort(sortedKeys);
            DecimalFormat df = new DecimalFormat("##0.00%");
            for( NGData item : sortedKeys ){
                ChatColor color = ChatColor.WHITE;
                if( item.getPriority() <= 0 ){
                    color = ChatColor.GRAY;
                }
                String send = color + item.getName() + ChatColor.GRAY + "("+item.getId()+")" + ChatColor.RESET + ChatColor.GREEN + ": " + ChatColor.YELLOW + "[ "+item.getPriority()+" / " + df.format(perList.get(item))+" | +"+item.getBonusPoint()+" ]";
                if( sender instanceof Player && sender.hasPermission(NGBuilder.getGameMasterPermission()) ){
                    TextComponent base = new TextComponent(""), remove = new TextComponent( "[X]" );
                    remove.setBold(true);
                    remove.setColor( net.md_5.bungee.api.ChatColor.RED );

                    remove.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/builder ng remove "+ item.getId()) );

                    remove.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "/builder ng remove "+ item.getId() +"\nクリックで実行します。" ).create() ) );

                    base.addExtra(remove);
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
            Utility.sendCmdHelp(sender, "/builder ng edit <NG>", "NGの編集をします。");
            Utility.sendCmdHelp(sender, "/builder ng create <NG>", "NGの作成をします。");
            Utility.sendCmdHelp(sender, "/builder ng remove <NG>", "NGの削除をします。");
        }
        Utility.sendCmdHelp(sender, "/builder ng info <NG>", "NGの説明が参照できます。");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        // b t a o a
        if( args.length == 2 ){
            if(sender.hasPermission(NGBuilder.getGameMasterPermission())){
                for (String name : new String[]{
                        "edit", "create", "remove"
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
            if( args[1].equalsIgnoreCase("remove") ||args[1].equalsIgnoreCase("edit") || args[1].equalsIgnoreCase("info") ){
                if( (args[1].equalsIgnoreCase("remove")||args[1].equalsIgnoreCase("edit")) && !sender.hasPermission(NGBuilder.getGameMasterPermission() )) return out;
                for (NGData ng : NGData.getAllNGData()) {
                    if (ng.getId().toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(ng.getId());
                    }
                }
            }
        } else if(args.length == 4){
            if( args[1].equalsIgnoreCase("edit")  ){
                if( !sender.hasPermission(NGBuilder.getGameMasterPermission() )) return out;
                for (String name : new String[]{
                        "name","shortName","startDesc","desc","mode",
                        "priority","bonus","penalty","countMode","registerMode"}){
                    if (name.toLowerCase().startsWith(args[3].toLowerCase())) {
                        out.add(name);
                    }
                }
            }
        } else if(args.length == 5){
            if( args[1].equalsIgnoreCase("edit")){
                if( args[3].equalsIgnoreCase("mode") ){
                    if (!sender.hasPermission(NGBuilder.getGameMasterPermission())) return out;
                    for (NGData.NGMode ng : NGData.NGMode.values()) {
                        if (ng.toString().toLowerCase().startsWith(args[4].toLowerCase())) {
                            out.add(ng.toString());
                        }
                    }
                } else if( args[3].equalsIgnoreCase("countMode") ){
                    if (!sender.hasPermission(NGBuilder.getGameMasterPermission())) return out;
                    for (NGData.CountDenyMode ng : NGData.CountDenyMode.values()) {
                        if (ng.toString().toLowerCase().startsWith(args[4].toLowerCase())) {
                            out.add(ng.toString());
                        }
                    }
                }
            }
        }
        return out;
    }
}
