package io.github.axtuki1.ngbuilder.command;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.util.Utility;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CleanCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(GameStatus.getStatus().equals(GameStatus.Playing)){
            PlayerData pd = GamePlayers.getData( ((Player)sender) );
            if( pd.isBuilder() ){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        Utility.AreaCleaner(
                                GameConfig.canBuilderPlacePoint1.getLocation(),
                                GameConfig.canBuilderPlacePoint2.getLocation()
                        );

                        GameConfig.canBuilderPlacePoint1.getLocation().getWorld().getEntities().forEach(entity -> {
                            if( !(entity instanceof Player) ){
                                if( Utility.isLocationArea(entity.getLocation(),
                                        GameConfig.canBuilderPlacePoint1.getLocation(),
                                        GameConfig.canBuilderPlacePoint2.getLocation())
                                ){
                                    entity.remove();
                                }
                            }
                        });
                    }
                }.runTask(NGBuilder.getMain());
                Bukkit.broadcastMessage(NGBuilder.getPrefix() + ChatColor.GREEN + "白紙に戻しました。");
            } else {
                sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "あなたは建築者ではありません。");
            }
        } else {
            sender.sendMessage(NGBuilder.getPrefix() + ChatColor.RED + "現在ゲーム中ではありません。");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
