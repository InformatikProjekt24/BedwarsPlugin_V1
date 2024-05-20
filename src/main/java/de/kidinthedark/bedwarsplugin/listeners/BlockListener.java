package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!event.getPlayer().isVisibleByDefault()) {
            event.setCancelled(true);
            return;
        }
        BedwarsPlugin.instance.gameManager.handleBlockBreak(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!event.getPlayer().isVisibleByDefault()) {
            event.setCancelled(true);
            return;
        }
        BedwarsPlugin.instance.gameManager.handleBlockPlace(event);
    }

}
