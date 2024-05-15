package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.util.ConfigVars;

import java.util.HashMap;

public class GameManager {

    private GameState gameState = GameState.PRELOBBY;

    private int lobbycountdown = ConfigVars.lobbycountdown;
    private int pregamecountdown = ConfigVars.pregamecountdown;
    private int postgamecountdown = ConfigVars.postgamecountdown;

    private boolean lobby_wait = true;
    private boolean ready_lobby = false;

    private HashMap<String, Boolean> teamBeds = new HashMap<>();

    public void tick() {
        if(gameState.equals(GameState.LOBBY)) {
            if(!lobby_wait) {
                lobbycountdown--;
            }
            if(lobbycountdown == 0) {
                doPregameTasks();
            }
        }
    }

    public void doPregameTasks() {

    }



}
