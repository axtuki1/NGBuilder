package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.system.NGData;
import org.bukkit.Location;
import org.bukkit.Material;

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
    }

    public void resetStopTime(){
        stopTime = 0;
    }
}
