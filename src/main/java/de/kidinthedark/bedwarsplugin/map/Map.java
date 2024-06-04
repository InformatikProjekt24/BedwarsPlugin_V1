package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.game.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;

public record Map(String name, String displayName, ArrayList<GameTeam> teams, ArrayList<Generator> generators) {

    public World getWorld() {
        return Bukkit.getWorld(name);
    }

}
