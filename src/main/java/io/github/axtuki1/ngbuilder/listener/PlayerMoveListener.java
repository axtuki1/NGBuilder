package io.github.axtuki1.ngbuilder.listener;

import io.github.axtuki1.ngbuilder.GameStatus;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.player.GamePlayers;
import io.github.axtuki1.ngbuilder.task.BaseTask;
import io.github.axtuki1.ngbuilder.task.MainTimerTask;
import io.github.axtuki1.ngbuilder.task.StopTimeTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(PlayerMoveEvent event){
        if(GameStatus.getStatus().equals(GameStatus.Playing)){
            MainTimerTask task = (MainTimerTask) NGBuilder.getTask();
            BaseTask subTask = task.getSubTask();
            if( subTask != null ){
                if( subTask instanceof StopTimeTask ){
                    if(GamePlayers.getData(event.getPlayer()).isBuilder()){
                        ((StopTimeTask)subTask).resetStopTime();
                    }
                }
            }
        }
    }

}
