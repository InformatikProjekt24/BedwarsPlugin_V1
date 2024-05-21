package de.kidinthedark.bedwarsplugin.map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Generator {

    private final Location location;
    private final Material material;
    private final int interval;
    private int intervalCount;

    public Generator(Location location, Material material, int interval) {
        this.location = location;
        this.material = material;
        this.interval = interval;
        this.intervalCount = interval;
    }

    public void tick() {
        intervalCount--;
        if (intervalCount == 0) {
            intervalCount = interval;
            ItemStack is = new ItemStack(material);
            location.getWorld().dropItem(location, is);
        }
    }

}

