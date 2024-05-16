package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.util.ConfigVars;

import java.util.HashMap;

public class GameManager {

    private GameState gameState = GameState.PRELOBBY;
    private boolean busy = false;

    private int lobbycountdown = ConfigVars.lobbycountdown;
    private int pregamecountdown = ConfigVars.pregamecountdown;
    private int postgamecountdown = ConfigVars.postgamecountdown;

    private boolean lobby_wait = true;
    private boolean ready_lobby = false;

    private HashMap<String, Boolean> teamBeds = new HashMap<>();

    public void tick() {
        if(busy) return;

        if(gameState.equals(GameState.LOBBY)) {
            if(!lobby_wait) {
                lobbycountdown--;
            }
            if(lobbycountdown == 0) {
                doPregameTasks();
            }
        }
    }

    public void prepareServer() {
        busy = true;
    }

    public void doPregameTasks() {

        busy = true;

    }


    //GETTERS
    public GameState getGameState() {
        return gameState;
    }
}
