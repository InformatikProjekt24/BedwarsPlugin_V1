package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameTeam {

    private boolean hasBed;
    private final boolean isAlive;

    private final Color color;
    private final Location spawn;
    private final Location bed1;
    private final Location bed2;
    private final Location teamShop;
    private final Location upgradeShop;

    private final ArrayList<Player> members;

    public GameTeam(Location spawn, Location bed1, Location bed2, Location teamShop, Location upgradeShop, Color color) {
        this.hasBed = true;
        this.isAlive = true;
        this.spawn = spawn;
        this.bed1 = bed1;
        this.bed2 = bed2;
        this.teamShop = teamShop;
        this.upgradeShop = upgradeShop;
        this.color = color;
        this.members = new ArrayList<>();
    }

    public void setMembers(ArrayList<Player> members) {
        this.members.clear();
        this.members.addAll(members);
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getTeamShop() {return teamShop; }

    public Location getUpgradeShop() {return upgradeShop; }

    public Color getColor() {return color; }

    public String getColorString() {
        return null;
        //TODO FIND SOLUTION
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean hasBed() {
        return hasBed;
    }

    public boolean isBedLocation(Location location) {
        return location.equals(bed1) || location.equals(bed2);
    }

    public void breakBed(Player p) {

        LanguagePlaceholder placer = new LanguagePlaceholder();
        placer.updatePlaceholder("team", "color_" + color.toString());

        if(members.contains(p)) {
            MessageFactory.sendMessage("game_action_bed_break_own", placer, p);
            return;
        }

        MessageFactory.sendMessage("game_action_bed_break", placer, p);

        this.hasBed = false;

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
            if(members.contains(player)) {
                MessageFactory.sendMessage("game_action_bed_broke_own", placer, player);
            } else {
                MessageFactory.sendMessage("game_action_bed_broke", placer, player);
            }
        }
    }

    public boolean hasMember(Player p) {
        return members.contains(p);
    }

}
