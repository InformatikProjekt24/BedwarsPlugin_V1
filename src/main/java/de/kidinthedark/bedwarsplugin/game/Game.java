package de.kidinthedark.bedwarsplugin.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private final ArrayList<GameTeam> playerTeams;

    public final HashMap<Player, Integer> playerKills;
    public final HashMap<Player, Integer> playerFinalKills;
    public final HashMap<Player, Integer> playerDeaths;

    public Game(ArrayList<GameTeam> playerTeams) {
        this.playerTeams = playerTeams;
        this.playerKills = new HashMap<>();
        this.playerFinalKills = new HashMap<>();
        this.playerDeaths = new HashMap<>();
    }

    public ArrayList<GameTeam> getTeams() {
        return playerTeams;
    }

    public void incrementPlayerKills(Player player) {
        playerKills.put(player, playerKills.get(player) + 1);
    }

    public void incrementPlayerFinalKills(Player player) {
        playerFinalKills.put(player, playerFinalKills.get(player) + 1);
        playerKills.put(player, playerKills.get(player) + 1);
    }

    public void incrementPlayerDeaths(Player player) {
        playerDeaths.put(player, playerDeaths.get(player) + 1);
    }

    public int getPlayerKills(Player player) {
        return playerKills.get(player);
    }

    public int getPlayerFinalKills(Player player) {
        return playerFinalKills.get(player);
    }

    public int getPlayerDeaths(Player player) {
        return playerDeaths.get(player);
    }
}
