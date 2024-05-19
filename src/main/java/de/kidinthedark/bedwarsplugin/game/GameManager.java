package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class GameManager {

    private GameState gameState = GameState.PRELOBBY;
    private boolean busy = false;

    private int lobbycountdown = ConfigVars.lobbycountdown;
    private final int pregamecountdown = ConfigVars.pregamecountdown;
    private final int postgamecountdown = ConfigVars.postgamecountdown;
    private boolean lobby_wait = true;

    private final HashMap<String, Boolean> teamBeds = new HashMap<>();

    public void tick() {
        if(busy) return;

        if(BedwarsPlugin.instance.mapManager.isReady() && gameState == GameState.PRELOBBY) {
            gameState = GameState.LOBBY;
        }

        if(gameState.equals(GameState.LOBBY)) {
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
        }

        if(gameState.equals(GameState.PREGAME)) {

        }
    }

    public void startLobbyCountdown() {
        if(gameState.equals(GameState.LOBBY)) {
            lobby_wait = false;
        }
    }

    public void prepareServer() {
        busy = true;
    }

    public void doPregameTasks() {
        busy = true;
        gameState = GameState.PREGAME;

    }


    //GETTERS
    public GameState getGameState() {
        return gameState;
    }

    public boolean allowJoin() {
        return gameState.equals(GameState.LOBBY);
    }
}
