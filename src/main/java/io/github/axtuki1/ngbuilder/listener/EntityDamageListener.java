package io.github.axtuki1.ngbuilder.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event){
        if( event.getEntity() instanceof Player){
//            if( event.getCause().equals(EntityDamageEvent.DamageCause.FALL) ) {
                event.setCancelled(true);
//            }
        }
    }

}
