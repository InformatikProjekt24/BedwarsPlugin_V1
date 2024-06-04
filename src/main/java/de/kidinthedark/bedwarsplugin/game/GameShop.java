package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class GameShop {

    private GameShopType gameShopType;
    private Location location;
    private Villager entity;
    private boolean isSpawned;

    public GameShop(GameShopType type, Location location) {
        this.gameShopType = type;
        this.location = location;
    }

    public void handleInteract(Player p) {

    }

    public static void handleInventoryInteract(InventoryInteractEvent e) {

    }

    public void spawn() {
        entity = (Villager) BedwarsPlugin.instance.mapManager.getLoadedMap().getWorld().spawnEntity(location, EntityType.VILLAGER);
        entity.setSilent(true);
        entity.setInvulnerable(true);
        entity.setNoPhysics(true);
        entity.setAI(false);
    }

    public Entity getEntity() {
        return entity;
    }
}
