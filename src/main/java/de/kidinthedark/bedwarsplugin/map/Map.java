package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.game.GameTeam;
import de.kidinthedark.bedwarsplugin.map.generators.Generator;

import java.util.ArrayList;

public class Map {

    private final ArrayList<GameTeam> teams;
    private final ArrayList<Generator> generators;
    private final String name;

    public Map(String name, ArrayList<GameTeam> teams, ArrayList<Generator> generators) {
        this.name = name;
        this.teams = teams;
        this.generators = generators;
    }

    public String getName() {
        return name;
    }

    public ArrayList<GameTeam> getTeams() {
        return teams;
    }

    public ArrayList<Generator> getGenerators() {
        return generators;
    }

}
