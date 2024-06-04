package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.game.GameShop;
import de.kidinthedark.bedwarsplugin.game.GameState;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
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

        LanguagePlaceholder placeholder = new LanguagePlaceholder();
        placeholder.updatePlaceholder("player", e.getPlayer().getName());
        placeholder.updatePlaceholder("killer", Objects.requireNonNull(e.getEntity().getKiller()).getName());

        if(BedwarsPlugin.instance.gameManager.getPlayerTeam(e.getPlayer()).hasBed()) {
            MessageFactory.broadcastMessage("game_player_killed", placeholder);
        } else {
            MessageFactory.broadcastMessage("game_player_final_killed", placeholder);
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
        if(!BedwarsPlugin.instance.gameManager.getGameState().equals(GameState.INGAME)) {
            e.setCancelled(true);
            return;
        }

        if(e.getEntity() instanceof Player) {
            if(e.getDamageSource().getCausingEntity() instanceof Player p) {
                if(!p.isVisibleByDefault()) {
                    e.setCancelled(true);
                }
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {

        e.setCancelled(true);

        for(GameShop shop : BedwarsPlugin.instance.gameManager.getShops()) {
            if(e.getRightClicked().equals(shop.getEntity())) {
                shop.handleInteract(e.getPlayer());
            }
        }


    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {
        GameShop.handleInventoryInteract(e);
    }



}
