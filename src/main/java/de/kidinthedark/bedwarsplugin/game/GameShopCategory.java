package de.kidinthedark.bedwarsplugin.game;

import org.bukkit.Material;

public enum GameShopCategory {

    BLOCKS("Blocks", Material.TERRACOTTA, 1),
    WEAPONS("Weapons", Material.GOLDEN_SWORD, 2),
    ARMOR("Armor", Material.IRON_BOOTS, 3),
    TOOLS("Tools", Material.STONE_PICKAXE, 4),
    BOWS("Bows", Material.BOW, 5),
    POTIONS("Potions", Material.BREWING_STAND, 6),
    EXTRA("Extra", Material.TNT, 7);

    final String name;
    final Material m;
    final int slot;

    private GameShopCategory(String name, Material m, int slot) {
        this.name = name;
        this.m = m;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return m;
    }

    public int getSlot() {
        return slot;
    }

    public String getLore() {
        return "§7Click here to open the category";
    }
}
