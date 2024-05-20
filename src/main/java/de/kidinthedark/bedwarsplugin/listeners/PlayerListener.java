package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.deathMessage(Component.empty());
        e.setDroppedExp(0);
        e.getDrops().clear();
        e.setReviveHealth(20);
        e.getPlayer().getInventory().clear();
        e.getPlayer().teleport(BedwarsPlugin.instance.gameManager.getPlayerTeam(e.getPlayer()).getSpawn());

        BedwarsPlugin.instance.gameManager.getGame().incrementPlayerDeaths(e.getPlayer());

        if(BedwarsPlugin.instance.gameManager.getPlayerTeam(e.getPlayer()).hasBed()) {

        } else {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.equals(e.getPlayer())) continue;
                p.hidePlayer(BedwarsPlugin.instance, e.getPlayer());
            }
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            e.getPlayer().setAllowFlight(true);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player player) {
            if(e.getDamageSource().getCausingEntity() instanceof Player p) {
                if(!p.isVisibleByDefault()) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
