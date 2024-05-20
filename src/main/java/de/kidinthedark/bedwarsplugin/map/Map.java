package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.game.GameTeam;
import de.kidinthedark.bedwarsplugin.map.generators.Generator;

import java.util.ArrayList;

public class Map {

    private ArrayList<GameTeam> teams;
    private ArrayList<Generator> generators;

    public Map(ArrayList<GameTeam> teams, ArrayList<Generator> generators) {
        this.teams = teams;
        this.generators = generators;
    }

    public ArrayList<GameTeam> getTeams() {
        return teams;
    }

    public ArrayList<Generator> getGenerators() {
        return generators;
    }

}
