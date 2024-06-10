package de.kidinthedark.bedwarsplugin.game;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ItemUtils {

    public static ItemStack getItem(Material m, String name, String lore, int damage, int amount) {

        ItemStack is = new ItemStack(m, amount, (short) damage);
        ItemMeta im = is.getItemMeta();

        if (lore != null) {
            if (lore.contains("\n")) {
                ArrayList<String> lorelist = new ArrayList<>();
                String[] loresplit = lore.split("\n");

                Collections.addAll(lorelist, loresplit);

                im.setLore(lorelist);

            } else {
                im.setLore(Arrays.asList(lore));
            }
        }

        im.setDisplayName(name);

        is.setItemMeta(im);

        return is;
    }

}
