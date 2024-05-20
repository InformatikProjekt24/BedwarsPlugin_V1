package de.kidinthedark.bedwarsplugin.game;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {

    private boolean hasBed;
    private boolean isAlive;
    private List<Player> members;

    private Color color;
    private Location spawn;
    private Location bed1;
    private Location bed2;

    public GameTeam(Location spawn, Location bed1, Location bed2, Color color) {
        this.hasBed = true;
        this.isAlive = true;
        this.spawn = spawn;
        this.bed1 = bed1;
        this.bed2 = bed2;
        this.color = color;
        members = new ArrayList<>();
    }

}
