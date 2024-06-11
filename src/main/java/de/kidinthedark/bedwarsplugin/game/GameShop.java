package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameShop {

private final GameShopType gameShopType;
private final Location location;
private Villager entity;
private boolean isSpawned;

public GameShop(GameShopType type, Location location) {
    this.gameShopType = type;
    this.location = location;
}

public void handleInteract(Player p) {

    if(gameShopType.equals(GameShopType.TEAM)) {

        Inventory inv = Bukkit.createInventory(null, 6*9, "Team Shop");

        for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemUtils.getItem(Material.GRAY_STAINED_GLASS_PANE, "", null, 7, 1));

        for (GameShopCategory cat : GameShopCategory.values()) {
            inv.setItem(cat.getSlot(), ItemUtils.getItem(cat.getMaterial(), cat.getName(), cat.getLore(), 1, 1));
        }

        p.openInventory(inv);

    } else if (gameShopType.equals(GameShopType.UPGRADE)) {
        
        Inventory inv = Bukkit.createInventory(null, 3*9, "Upgrade Shop");

        for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemUtils.getItem(Material.GRAY_STAINED_GLASS_PANE, "", null, 7, 1));

        for (UpgradeShopCategory cat : UpgradeShopCategory.values()) {
            inv.setItem(cat.getSlot(), ItemUtils.getItem(cat.getMaterial(), cat.getName(), cat.getLore(), 1, 1));
        }

        p.openInventory(inv);
    }

}

public static void handleInventoryInteract(InventoryClickEvent e) {

    e.setCancelled(true);

    String itemShopTitle = "Team Shop";
    String upgradeShopTitle = "Upgrade Shop";

    Player player = (Player) e.getWhoClicked();

    if(player.getOpenInventory().title().equals(Component.text(itemShopTitle))) {
        handleItemShop(player, e.getCurrentItem(), e.getClick());
    } else if (player.getOpenInventory().title().equals(Component.text(upgradeShopTitle))) {
        handleUpgradeShop(player, e.getCurrentItem(), e.getClick());
    }

}

private static void handleItemShop(Player player, ItemStack clickedItem, ClickType clickType) {
    if(clickType == ClickType.LEFT) {
        /*if (player.getOpenInventory().getItem(clickedItem).getSlot() < 9) {
            //todo: change to according item shop
        } else {
            //todo: buy
        }*/
    } else if(clickType == ClickType.SHIFT_LEFT) {
        //TODO buy stack or max possible (whichever is smaller)
    }
}

private static void handleUpgradeShop(Player player, ItemStack clickedItem, ClickType clickType) {
    if(clickType == ClickType.LEFT) {
        //TODO BUY
    } else if(clickType == ClickType.SHIFT_LEFT) {
        //TODO buy stack or max possible (whichever is smaller)
    }
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
