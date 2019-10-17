package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class BuilderOptionCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if( args.length == 1 ) {
            sender.sendMessage(ChatColor.RED + "======================================");
            for (GameConfig config : GameConfig.values()) {
                if (!config.canCommandChange()) {
                    continue;
                }
                if(config.isBoolean()){
                    sender.sendMessage(
                            Utility.TextToggle(
                                    ChatColor.YELLOW + config.getName(),
                                    Boolean.parseBoolean(config.getValue())
                            )
                    );
                } else {
                    sender.sendMessage(
                            ChatColor.YELLOW + config.getName() + ": " + ChatColor.GREEN + config.getValue()
                    );
                }
            }
            sender.sendMessage(ChatColor.RED + "======================================");
        } else {
            if( args[1].equalsIgnoreCase("DifficultyMin") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "難易度を指定してください。");
                } else {
                    try{
                        GameConfig.DifficultyMin.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最小難易度を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("DifficultyMax") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "難易度を指定してください。");
                } else {
                    try{
                        GameConfig.DifficultyMax.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最大難易度を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("BlockCountMin") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "ブロック数を指定してください。");
                } else {
                    try{
                        GameConfig.BlockCountMin.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最小ブロック数を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("BlockCountMax") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "ブロック数を指定してください。");
                } else {
                    try{
                        GameConfig.BlockCountMax.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最大ブロック数を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("StopTimeMin") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "秒数を指定してください。");
                } else {
                    try{
                        GameConfig.BlockCountMin.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最小秒数を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("StopTimeMax") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "秒数を指定してください。");
                } else {
                    try{
                        GameConfig.BlockCountMax.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "最大秒数を "+args[2]+" に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("Cycle") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "回数を指定してください。");
                } else {
                    try{
                        GameConfig.Cycle.set(Integer.parseInt(args[2]));
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "1人"+args[2]+"回に変更しました。");
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
                    }
                }
            } else if( args[1].equalsIgnoreCase("AllowAllGenre") ){
                Utility.SwitchConfig(
                        args,
                        sender,
                        "AllowAllGenre",
                        "すべてのジャンルを出現させるように変更しました。",
                        "限定したジャンルのみ出現させるように変更しました。"
                );
            } else if( args[1].equalsIgnoreCase("ShowGenre") ){
                Utility.SwitchConfig(
                        args,
                        sender,
                        "ShowGenre",
                        "ジャンルを表示させるように変更しました。",
                        "ジャンルを表示しないように変更しました。"
                );
            } else if( args[1].equalsIgnoreCase("ShowDifficulty") ){
                Utility.SwitchConfig(
                        args,
                        sender,
                        "ShowDifficulty",
                        "難易度を表示させるように変更しました。",
                        "難易度を表示しないように変更しました。"
                );
            } else if( args[1].equalsIgnoreCase("DuplicateTheme") ){
                Utility.SwitchConfig(
                        args,
                        sender,
                        "DuplicateTheme",
                        "お題の重複を許可しました。",
                        "お題の重複を禁止しました。"
                );
            } else if( args[1].equalsIgnoreCase("RoundTime") ){
                if( args.length == 2 ){
                    sender.sendMessage(NGBuilder.getPrefix() + "秒数を指定してください。");
                } else {
                    try{
                        if( Integer.parseInt(args[2]) <= 0 ){
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "駄目です。");
                        } else {
                            GameConfig.RoundTime.set(Integer.parseInt(args[2]));
                            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "1ラウンドの時間を "+args[2]+"秒 に変更しました。");
                        }
                    } catch (NumberFormatException e){
                        sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "数値で指定して下さい！");
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
            for (GameConfig c : GameConfig.values()) {
                if( c.canCommandChange() ){
                    if (c.getPath().toLowerCase().startsWith(args[1].toLowerCase())) {
                        out.add(c.getPath());
                    }
                }
            }
        } else if(args.length == 3){
            if( !args[1].equalsIgnoreCase("GameTime") &&
                    !args[1].equalsIgnoreCase("Difficulty")  ){
                for (String name : new String[]{
                        "true","false"
                }) {
                    if (name.toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(name);
                    }
                }
            }
        }
        return out;
    }
}
