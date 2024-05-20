package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.game.GameTeam;
import de.kidinthedark.bedwarsplugin.map.generators.Generator;

import java.util.ArrayList;

public record Map(String name, String displayName, ArrayList<GameTeam> teams, ArrayList<Generator> generators) {

}
