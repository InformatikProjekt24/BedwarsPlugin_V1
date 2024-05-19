package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class GameManager {

    private GameState gameState = GameState.LOBBY;
    private boolean busy = false;

    private int lobbycountdown = ConfigVars.lobbycountdown;
    private int pregamecountdown = ConfigVars.pregamecountdown;
    private int postgamecountdown = ConfigVars.postgamecountdown;
    private boolean lobby_wait = true;

    private final HashMap<String, Boolean> teamBeds = new HashMap<>();

    public void tick() {
        if(busy) return;

        switch (gameState) {
            case PRELOBBY:
                if(BedwarsPlugin.instance.mapManager.isReady()) gameState = GameState.LOBBY;
                break;
            case LOBBY:
                if((!lobby_wait || Bukkit.getOnlinePlayers().size() >= ConfigVars.playersRequired) && lobbycountdown != 0) {
                    lobbycountdown--;
                    LanguagePlaceholder placeholder = new LanguagePlaceholder();
                    placeholder.updatePlaceholder("time", String.valueOf(lobbycountdown));
                    placeholder.updatePlaceholder("timeUnit", (lobbycountdown==1) ? "util_unit_second" : "util_unit_seconds");
                    MessageFactory.broadcastMessage("lobby_info_countdown", placeholder);
                }
                if(lobbycountdown == 0) {
                    doPregameTasks();
                }
                break;
            case PREGAME:
                if(pregamecountdown != 0) {
                    pregamecountdown--;
                } else {
                    prepareGame();
                }
                break;
            case INGAME:
                tickIngameTasks();
                break;
            case POSTGAME:
                if(postgamecountdown != 0) {
                    postgamecountdown--;
                } else {
                    doEndlobbyTasks();
                }
                break;
            case ENDLOBBY:

                break;
            case POSTLOBBY:

                break;
        }

    }

    public boolean startLobbyCountdown() {
        if(gameState.equals(GameState.LOBBY)) {
            lobby_wait = false;
        }

        return gameState.equals(GameState.LOBBY);
    }

    public void prepareServer() {
        busy = true;
    }

    public void doPregameTasks() {
        busy = true;
        gameState = GameState.PREGAME;

    }

    public void prepareGame() {

    }

    public void doPostgameTasks() {
        busy = true;
        gameState = GameState.POSTGAME;
    }

    public void doEndlobbyTasks() {
        busy = true;
        gameState = GameState.ENDLOBBY;
    }

    public void tickIngameTasks() {
        if(!gameState.equals(GameState.INGAME)) return;



    }


    //GETTERS
    public GameState getGameState() {
        return gameState;
    }

    public boolean allowJoin() {
        return gameState.equals(GameState.LOBBY);
    }

}
