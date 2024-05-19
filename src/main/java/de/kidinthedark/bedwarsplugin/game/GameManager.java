package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.util.ConfigVars;

import java.util.HashMap;

public class GameManager {

    private final GameState gameState = GameState.PRELOBBY;
    private boolean busy = false;

    private int lobbycountdown = ConfigVars.lobbycountdown;
    private final int pregamecountdown = ConfigVars.pregamecountdown;
    private final int postgamecountdown = ConfigVars.postgamecountdown;

    private final boolean ready_lobby = false;

    private final HashMap<String, Boolean> teamBeds = new HashMap<>();

    public void tick() {
        if(busy) return;

        if(gameState.equals(GameState.LOBBY)) {
            boolean lobby_wait = true;
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
