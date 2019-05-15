package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.ThemeData;
import io.github.axtuki1.ngbuilder.task.MainTimerTask;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderThemeCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length <= 1 ){
            sendCmdHelp(sender);
            return true;
        }
        if( args[1].equalsIgnoreCase("add") ){
            if( args.length <= 3 ){
                sendCmdHelp(sender);
                return true;
            }
            if( args.length >= 8 ){
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "スペースを含むことはできません。");
                return true;
            }
            if( args[3].matches("^[\\u3040-\\u309F|\\u30FC]+$") ){
                try{
                    ThemeData td = new ThemeData(
                            args[2],
                            args[3],
                            Integer.parseInt(args[4]),
                            ( args.length >= 6 ? Double.parseDouble(args[5]) : 1 ),
                            ( args.length >= 7 ? Integer.parseInt(args[6]) : 0 )
                    );
                    GameConfig.ThemeDataList.addTheme(
                            td
                    );
                    sender.sendMessage(NGBuilder.getPrefix() + "追加: "+td.getGenre()+": お題「"+td.getTheme()+"」難:"+td.getDifficulty()+" 倍:"+td.getBonusPer()+" 加:"+td.getBonusAdd()+"");
                    if( sender instanceof Player ){
                        ((Player)sender).playSound(((Player)sender).getLocation(), Sound.BLOCK_NOTE_PLING, 1 , (float) 2);
                    }
                } catch ( NumberFormatException e ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値である場所が数値ではありません。");
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "お題にはスペースを使用できません。");
                }
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "すべてひらがなではありません！");
            }
        } else if( args[1].equalsIgnoreCase("remove") ){
            if( args.length >= 3 ){
                try{
                    List<ThemeData> tdlist = GameConfig.ThemeDataList.getThemeList();
                    for( ThemeData td : tdlist ){
                        if( td.getTheme().equalsIgnoreCase(args[2]) ){
                            GameConfig.ThemeDataList.removeTheme(td.getKey());
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "お題["+td.getTheme() + " Key:" + td.getKey()+"] を削除しました。");
                            return true;
                        }
                    }
                    HashMap<String, ThemeData> tdlista = GameConfig.ThemeDataList.getThemeHashMap();
                    ThemeData td = tdlista.get(args[2]);
                    GameConfig.ThemeDataList.removeTheme(args[2]);
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "お題["+td.getTheme() + " Key:" + args[2]+"] を削除しました。");
                } catch (IllegalArgumentException e) {

                }
            } else {
                sendThemeList((Player) sender);
                sender.sendMessage(ChatColor.RED + "[引数エラー] " + ChatColor.WHITE + "引数が足りません。 [/builder theme remove <Key>]");
            }
        } else if( args[1].equalsIgnoreCase("list") ){
            sendThemeList(sender);
        } else if( args[1].equalsIgnoreCase("edit") ){
            // /bu 1Theme 2edit 3
            if( args.length <= 2 ){
                sendCmdHelp(sender);
            } else if( args.length <= 4  ){
                List<ThemeData> tdlist = GameConfig.ThemeDataList.getThemeList();
                for( ThemeData td : tdlist ){
                    if( td.getTheme().equalsIgnoreCase(args[2]) ){
                        sender.sendMessage(ChatColor.RED +"============================================================");
                        sender.sendMessage("");
                        sender.sendMessage("   "+ ChatColor.YELLOW +"キー     "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getKey());
                        sender.sendMessage("   "+ ChatColor.YELLOW +"ジャンル "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getGenre());
                        sender.sendMessage("   "+ ChatColor.YELLOW +"難易度   "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getDifficulty());
                        sender.sendMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getTheme());
                        sender.sendMessage("   " + ChatColor.YELLOW + "倍率     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getBonusPer());
                        sender.sendMessage("   " + ChatColor.YELLOW + "加点     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getBonusAdd());
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.RED +"============================================================");
                        return true;
                    }
                }
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + args[2] + " というお題は見つかりませんでした。");
            } else {
                ThemeData td = null;
                List<ThemeData> tdlist = GameConfig.ThemeDataList.getThemeList();
                for( ThemeData tdd : tdlist ){
                    if( tdd.getTheme().equalsIgnoreCase(args[2]) ){
                        td = tdd;
                    }
                }
                if( td == null ){
                    sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + args[2] + " というお題は見つかりませんでした。");
                } else {
                    if( args[3].equalsIgnoreCase("difficulty") ){
                        td.setDifficulty( Integer.parseInt(args[4]) );
                    }
                    if( args[3].equalsIgnoreCase("genre") ){
                        td.setGenre( args[4] );
                    }
                    if( args[3].equalsIgnoreCase("per") ){
                        td.setBonusPer( Double.parseDouble(args[4]) );
                    }
                    if( args[3].equalsIgnoreCase("add") ){
                        td.setBonusAdd( Integer.parseInt(args[4]) );
                    }
                    GameConfig.ThemeDataList.addTheme(
                            td
                    );
                    sender.sendMessage(ChatColor.RED +"============================================================");
                    sender.sendMessage("");
                    sender.sendMessage("   " + ChatColor.YELLOW + "更新しました！");
                    sender.sendMessage("   "+ ChatColor.YELLOW +"キー     "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getKey());
                    sender.sendMessage("   "+ ChatColor.YELLOW +"ジャンル "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getGenre());
                    sender.sendMessage("   "+ ChatColor.YELLOW +"難易度   "+ChatColor.GREEN+": " + ChatColor.WHITE + td.getDifficulty());
                    sender.sendMessage("   " + ChatColor.YELLOW + "お題     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getTheme());
                    sender.sendMessage("   " + ChatColor.YELLOW + "倍率     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getBonusPer());
                    sender.sendMessage("   " + ChatColor.YELLOW + "加点     " + ChatColor.GREEN + ": " + ChatColor.WHITE + td.getBonusAdd());
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.RED +"============================================================");
                }
            }
        } else if( args[1].equalsIgnoreCase("GenreL") ){
            HashMap<String, ThemeData> tdlist = GameConfig.ThemeDataList.getThemeHashMap();
            sender.sendMessage(ChatColor.RED + "=======================================================================");
//        sender.sendMessage(ChatColor.YELLOW + "設定されているスポーンポイント");
            HashMap<String, HashMap<String, ThemeData>> getd = GameConfig.ThemeDataList.getThemeHashMapSortGenre();
            List<String> i = new ArrayList<>(getd.keySet());
            Collections.sort(i);
            for( String key : i ){
                HashMap<String, ThemeData> tdhash = getd.get(key);
                sender.sendMessage(ChatColor.YELLOW + key + ": "+ChatColor.GREEN + tdhash.size() +ChatColor.YELLOW+"件");
                for( String hashkey : tdhash.keySet() ){
                    ThemeData td = tdhash.get(hashkey);
                    sender.sendMessage( "  "+hashkey+": " + td.getTheme() +
                            " / 難:" +
                            td.getDifficulty() +
                            " 倍:" +
                            td.getBonusPer() +
                            " 加:" +
                            td.getBonusAdd());
                }
            }
            sender.sendMessage(ChatColor.YELLOW + "合計: "+ChatColor.GREEN+tdlist.size()+ChatColor.YELLOW+"件  ジャンル数: "+ChatColor.GREEN+getd.size()+ChatColor.YELLOW+"件");
            sender.sendMessage(ChatColor.RED + "=======================================================================");
        } else if( args[1].equalsIgnoreCase("DiffiL") ){
            if( args.length >= 3 ){
                List<ThemeData> tdlist = GameConfig.ThemeDataList.getThemeListFromDifficulty( Integer.parseInt(args[2]) );
                sender.sendMessage(ChatColor.RED + "=======================================================================");
                sender.sendMessage(ChatColor.YELLOW + "Lv." + args[2] + ": ");
                for( ThemeData td : tdlist ){
                    sender.sendMessage( "  "+td.getKey()+": " + td.getGenre() + ": " + td.getTheme() +
                            " / 難:" +
                            td.getDifficulty() +
                            " 倍:" +
                            td.getBonusPer() +
                            " 加:" +
                            td.getBonusAdd());
                }
                sender.sendMessage(ChatColor.YELLOW + "合計: "+ChatColor.GREEN+tdlist.size()+ChatColor.YELLOW+"件");
                sender.sendMessage(ChatColor.RED + "=======================================================================");
            } else {
                HashMap<String, ThemeData> tdlist = GameConfig.ThemeDataList.getThemeHashMap();
                sender.sendMessage(ChatColor.RED + "=======================================================================");
//        sender.sendMessage(ChatColor.YELLOW + "設定されているスポーンポイント");
                HashMap<Integer, HashMap<String, ThemeData>> getd = GameConfig.ThemeDataList.getThemeHashMapSortDifficulty();
                List<Integer> i = new ArrayList<>(getd.keySet());
                Collections.sort(i);
                for( int key : i ){
                    HashMap<String, ThemeData> tdhash = getd.get(key);
                    sender.sendMessage(ChatColor.YELLOW+"Lv." +ChatColor.YELLOW.toString() + key + ": "+ChatColor.GREEN + tdhash.size() +ChatColor.YELLOW+"件");
                    for( String hashkey : tdhash.keySet() ){
                        ThemeData td = tdhash.get(hashkey);
                        sender.sendMessage( "  "+hashkey+": " + td.getGenre() + ": " + td.getTheme() +
                                " / 難:" +
                                td.getDifficulty() +
                                " 倍:" +
                                td.getBonusPer() +
                                " 加:" +
                                td.getBonusAdd());
                    }
                }
                sender.sendMessage(ChatColor.YELLOW + "合計: "+ChatColor.GREEN+tdlist.size()+ChatColor.YELLOW+"件");
                sender.sendMessage(ChatColor.RED + "=======================================================================");
            }
        } else if(args[1].equalsIgnoreCase("blist")){
            HashMap<String, ThemeData> tdlist = GameConfig.ThemeDataList.getThemeHashMap();
            Bukkit.broadcastMessage(ChatColor.RED + "=======================================================================");
//        sender.sendMessage(ChatColor.YELLOW + "設定されているスポーンポイント");
            for( String key : tdlist.keySet() ){
                ThemeData td = tdlist.get(key);
                if( sender instanceof Player ){
                    TextComponent base = new TextComponent( " " );
                    TextComponent data = new TextComponent(
                            td.getGenre() + ": " + td.getTheme() +
                                    " / 難:" +
                                    td.getDifficulty() +
                                    " 倍:" +
                                    td.getBonusPer() +
                                    " 加:" +
                                    td.getBonusAdd()
                    );
                    base.addExtra( " "+key+": ");
                    base.addExtra(data);
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.spigot().sendMessage(base);
                    }
                } else {
                    sender.sendMessage("  "+key+": " + td.getGenre() + ": " + td.getTheme() +
                            " / 難:" +
                            td.getDifficulty() +
                            " 倍:" +
                            td.getBonusPer() +
                            " 加:" +
                            td.getBonusAdd() );
                }
            }
            Bukkit.broadcastMessage(ChatColor.YELLOW + "合計: "+ChatColor.GREEN+tdlist.size()+ChatColor.YELLOW+"件");
            Bukkit.broadcastMessage(ChatColor.RED + "=======================================================================");
        }
        return true;
    }

    private void sendCmdHelp(CommandSender sender) {
        Utility.sendCmdHelp(sender, "/builder theme add <ジャンル> <ひらがな> <難易度> [正解時倍率/1] [正解時単純加点/0]", "お題を追加します。(スペース使用不可)");
        Utility.sendCmdHelp(sender, "/builder theme remove <お題 or Key>", "お題を削除します。");
        Utility.sendCmdHelp(sender, "/builder theme edit <お題> <Difficulty|Genre|Per|Add> <値>", "お題を編集します。");
        Utility.sendCmdHelp(sender, "/builder theme list", "一覧を表示します。");
        Utility.sendCmdHelp(sender, "/builder theme GenreL", "ジャンル別一覧を表示します。");
        Utility.sendCmdHelp(sender, "/builder theme DiffiL", "難易度別一覧を表示します。");
    }

    private void sendThemeList(CommandSender sender) {
        HashMap<String, ThemeData> tdlist = GameConfig.ThemeDataList.getThemeHashMap();
        sender.sendMessage(ChatColor.RED + "=======================================================================");
//        sender.sendMessage(ChatColor.YELLOW + "設定されているスポーンポイント");
        for( String key : tdlist.keySet() ){
            ThemeData td = tdlist.get(key);
            if( sender instanceof Player ){
                TextComponent base = new TextComponent( " " );
                TextComponent remove = new TextComponent( "[X]" );
                remove.setColor(net.md_5.bungee.api.ChatColor.RED);
                remove.setBold(true);
                remove.setClickEvent(
                        new ClickEvent(
                                ClickEvent.Action.RUN_COMMAND, "/builder theme remove " + key
                        )
                );
                TextComponent data = new TextComponent(
                         td.getTheme() +
                                " / 難:" +
                                td.getDifficulty() +
                                " 倍:" +
                                td.getBonusPer() +
                                " 加:" +
                                td.getBonusAdd()
                );
                base.addExtra(remove);
                base.addExtra( " "+key+": ");
                base.addExtra(data);
                ((Player)sender).spigot().sendMessage(base);
            } else {
                sender.sendMessage( " "+key+": " + td.getTheme() +
                        " / 難:" +
                        td.getDifficulty() +
                        " 倍:" +
                        td.getBonusPer() +
                        " 加:" +
                        td.getBonusAdd());
            }
        }
        sender.sendMessage(ChatColor.YELLOW + "合計: "+ChatColor.GREEN+tdlist.size()+ChatColor.YELLOW+"件");
        sender.sendMessage(ChatColor.RED + "=======================================================================");
    }


    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        // b t a o a
        if( args.length == 2 ){
            for (String name : new String[]{
                    "add", "remove", "list", "blist", "GenreL", "edit", "DiffiL"
            }) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    out.add(name);
                }
            }
        } else if( args.length == 4 ){
            if( args[1].equalsIgnoreCase("edit") ){
                for (String name : new String[]{
                        "difficulty","genre","per", "add"
                }) {
                    if (name.toLowerCase().startsWith(args[3].toLowerCase())) {
                        out.add(name);
                    }
                }
            }
        }
        return out;
    }

}
