package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.system.NGData;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class StopTimeTask extends BaseTask {
    StopTimeTask(NGBuilder pl) {
        super(pl);
        BaseTask baseTask = NGBuilder.getTask();
        if( !(baseTask instanceof MainTimerTask) ){
            this.stop();
            this.cancel();
            return;
        }
        task = (MainTimerTask)baseTask;
        ng = task.getCurrentNGData();
        if( !ng.getNGMode().equals(NGData.NGMode.StopTimeDeny) ){
            this.stop();
            this.cancel();
            return;
        }
        if( ng.getCount() == 0 ){
            ng.genCount();
        }
    }

    private MainTimerTask task;

    private NGData ng;

    private int stopTime = 0, nextCount = 0;

    public int getStopTime() {
        return stopTime;
    }

    @Override
    public void exec() {
        if( nextCount == 20 ){
            execSecond();
            nextCount = 0;
        } else {
            nextCount++;
        }
    }

    private void execSecond() {
        stopTime++;
        int i = (ng.getCount() - stopTime);
        if( i <= 3 && i != 0 ){
            Player p = task.getBuilderPlayerData().getPlayer();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }
//        if(i == 2){
//            Player p = task.getBuilderPlayerData().getPlayer();
//            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
//                        }
//                    }.runTaskLater(NGBuilder.getMain(), 2);
//                }
//            }.runTaskLater(NGBuilder.getMain(), 2);
//        }
    }

    public void resetStopTime(){
        stopTime = 0;
    }
}
