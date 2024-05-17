package de.kidinthedark.bedwarsplugin.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    public GameState gameState = GameState.PRELOBBY;

    public List<GameTeam> teams = new ArrayList<>();

    public HashMap<Player, Integer> playerKills = new HashMap<>();
    public HashMap<Player, Integer> playerFinalKills = new HashMap<>();
    public HashMap<Player, Integer> playerDeaths = new HashMap<>();

}
