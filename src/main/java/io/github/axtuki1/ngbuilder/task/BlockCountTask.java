package io.github.axtuki1.ngbuilder.task;

import io.github.axtuki1.ngbuilder.GameConfig;
import io.github.axtuki1.ngbuilder.NGBuilder;
import io.github.axtuki1.ngbuilder.system.NGData;
import org.bukkit.Location;
import org.bukkit.Material;

public class BlockCountTask extends BaseTask {
    BlockCountTask(NGBuilder pl) {
        super(pl);
        BaseTask baseTask = NGBuilder.getTask();
        if( !(baseTask instanceof MainTimerTask) ){
            this.stop();
            this.cancel();
            return;
        }
        task = (MainTimerTask)baseTask;
        ng = task.getCurrentNGData();
        if( !ng.getNGMode().equals(NGData.NGMode.CountDeny) ){
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

    private int count = 0;

    @Override
    public void exec() {

        count = 0;

        Location point1 = GameConfig.canBuilderPlacePoint1.getLocation(),
                point2 = GameConfig.canBuilderPlacePoint2.getLocation();
        for (int xPoint = Math.min(point1.getBlockX(), point2.getBlockX()); xPoint <=
                Math.max(point1.getBlockX(), point2.getBlockX())
                ; xPoint++) {
            for (int yPoint = Math.min(point1.getBlockY(), point2.getBlockY()); yPoint <=
                    Math.max(point1.getBlockY(), point2.getBlockY())
                    ; yPoint++) {
                for (int zPoint = Math.min(point1.getBlockZ(), point2.getBlockZ()); zPoint <=
                        Math.max(point1.getBlockZ(), point2.getBlockZ())
                        ; zPoint++) {
                    Location loc = new Location(point1.getWorld() ,xPoint, yPoint, zPoint);
                    if( !loc.getBlock().getType().equals(Material.AIR) ){
                        byte data = loc.getBlock().getData();
                        Material mate = loc.getBlock().getType();
                        if( ng.getCountDenyMode().equals(NGData.CountDenyMode.Normal) ){
                            if( mate.equals(Material.WATER) || mate.equals(Material.STATIONARY_WATER) || mate.equals(Material.LAVA) || mate.equals(Material.STATIONARY_LAVA) ){
                                if( data == 0 ){
                                    count++;
                                }
                            } else if( mate.equals(Material.PISTON_EXTENSION) || mate.equals(Material.PISTON_MOVING_PIECE) ){
                                // do nothing.
                            } else {
                                count++;
                            }
                        } else if( ng.getCountDenyMode().equals( NGData.CountDenyMode.Hard ) ){
                            if( mate.equals(Material.WATER) || mate.equals(Material.STATIONARY_WATER) || mate.equals(Material.LAVA) || mate.equals(Material.STATIONARY_LAVA) ){
                                if( data == 0 ){
                                    count++;
                                }
                            } else {
                                count++;
                            }
                        } else if( ng.getCountDenyMode().equals( NGData.CountDenyMode.Hardcore ) ){
                            count++;
                        }
                    }
                }
            }
        }

    }

    public int getCount() {
        return count;
    }
}
