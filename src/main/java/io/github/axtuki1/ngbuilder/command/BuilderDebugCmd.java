package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import io.github.axtuki1.ngbuilder.system.BlockData;
import io.github.axtuki1.ngbuilder.system.NGData;
import io.github.axtuki1.ngbuilder.task.MainTimerTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuilderDebugCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 1 ){

        } else if(args[1].equalsIgnoreCase("m")) {
            if( GamePlayers.getDebuggingPlayers().contains( ((Player)sender).getUniqueId() ) ){
                sender.sendMessage( NGBuilder.getPrefix() + "Material表示モードを終了しました。" );
                GamePlayers.removeDebuggingPlayer( ((Player)sender).getUniqueId() );
            } else {
                Player p = ((Player)sender);
                sender.sendMessage( NGBuilder.getPrefix() + "Material表示モードに入りました。" );
                GamePlayers.addDebuggingPlayer( p.getUniqueId() );
            }
        } else if(args[1].equalsIgnoreCase("dng")) {
            GamePlayers.setCurrentDebuggingNGData(new NGData(args[2]));
        } else if(args[1].equalsIgnoreCase("mem")) {
            for( PlayerData pd : GamePlayers.getPlayers().values() ){
                pd.dump();
            }
        } else if( args[1].equalsIgnoreCase("ng") ){
            if( GameStatus.getStatus().equals(GameStatus.Playing) ){
                if( NGBuilder.getTask() != null ){
                    if( NGBuilder.getTask() instanceof MainTimerTask){
                        MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
                        task.setCurrentNGData(new NGData(args[2]));
                    }
                }
            }
        } else if( args[1].equalsIgnoreCase("p") ){
            NGData ng = new NGData(args[2]);
            Location loc = ((Player)sender).getLocation().add(0,-2,0);
            for(Material m : ng.getBlockDataList()){
                sender.sendMessage(m+"");
                loc.getWorld().dropItem(loc, new ItemStack(m));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<String>();
        if( args.length == 2 ){
            for (String name : new String[]{
                    "ng", "m", "dng", "p"
            }) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    out.add(name);
                }
            }
        }
        if( args.length == 3 ){
            if( args[1].equalsIgnoreCase("ng") || args[1].equalsIgnoreCase("dng") || args[1].equalsIgnoreCase("p") ){
                for (NGData ng : NGData.getAllNGData()) {
                    if (ng.getId().toLowerCase().startsWith(args[2].toLowerCase())) {
                        out.add(ng.getId());
                    }
                }
            }
        }
        return out;
    }
}
