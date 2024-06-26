package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.map.Generator;
import de.kidinthedark.bedwarsplugin.scoreboard.GameScoreboard;
import de.kidinthedark.bedwarsplugin.scoreboard.ScoreboardData;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private GameState gameState = GameState.LOBBY;
    private boolean busy;

    private int lobbycountdown;
    private int pregamecountdown;
    private int postgamecountdown;
    private boolean lobby_wait;

    private Game game;
    private GameScoreboard scoreboard;
    private volatile ScoreboardData scoreboardData;

    private final ArrayList<Block> placedBlocks;

    private final ArrayList<GameShop> shops;

    public GameManager() {
        lobbycountdown = ConfigVars.lobbycountdown;
        pregamecountdown = ConfigVars.pregamecountdown;
        postgamecountdown = ConfigVars.postgamecountdown;
        scoreboard = new GameScoreboard();
        scoreboardData = new ScoreboardData(ConfigVars.scoreboardLines, ConfigVars.scoreboardTitle);
        placedBlocks = new ArrayList<>();
        shops = new ArrayList<>();
        busy = false;
        lobby_wait = true;
    }

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

                    doLobbyCountdown(placeholder, "start");
                }
                if(lobbycountdown == 0) {
                    doPregameTasks();
                }
                break;
            case PREGAME:
                doPregameTick();
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
                lobbycountdown--;

                LanguagePlaceholder placeholder = new LanguagePlaceholder();
                placeholder.updatePlaceholder("time", String.valueOf(lobbycountdown));
                placeholder.updatePlaceholder("timeUnit", (lobbycountdown==1) ? "util_unit_second" : "util_unit_seconds");

                doLobbyCountdown(placeholder, "end");
                break;
            case POSTLOBBY:

                break;
        }

    }

    public void asyncTick() {
        scoreboard.sendScoreboard();
    }

    private void doLobbyCountdown(LanguagePlaceholder placeholder, String state) {
        switch (lobbycountdown) {
            case 30:
            case 15:
                MessageFactory.broadcastMessage("lobby_info_countdown_" + state, placeholder);
                break;
            default:
                if(lobbycountdown<=10) MessageFactory.broadcastMessage("lobby_info_countdown_" + state, placeholder);
                break;
        }
    }

    public void doPregameTick() {
        if(pregamecountdown != 0) {
            pregamecountdown--;
        } else {
            prepareGame();
        }

        Bukkit.getScheduler().runTaskAsynchronously(BedwarsPlugin.instance, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                Location loc = getPlayerTeam(p).getSpawn();
                p.teleport(loc);
            }
        });
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

        for(GameTeam team : game.getTeams()) {
            Location shopLocation = team.getTeamShop();
            Location upgradeLocation = team.getUpgradeShop();

            GameShop teamShop = new GameShop(GameShopType.TEAM, shopLocation);
            GameShop upgradeShop = new GameShop(GameShopType.UPGRADE, upgradeLocation);
            shops.addAll(List.of(teamShop, upgradeShop));
        }

        for(GameShop shop : shops) {
            shop.spawn();
        }

    }

    public void prepareGame() {
        busy = true;
        Bukkit.getScheduler().runTaskAsynchronously(BedwarsPlugin.instance, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                Location loc = getPlayerTeam(p).getSpawn();
                p.teleport(loc);
            }
        });
        busy = false;
    }

    public void doPostgameTasks() {
        busy = true;
        gameState = GameState.POSTGAME;
    }

    public void doEndlobbyTasks() {
        busy = true;
        gameState = GameState.ENDLOBBY;
        lobbycountdown = ConfigVars.lobbycountdown/2;
    }

    public void tickIngameTasks() {
        if(!gameState.equals(GameState.INGAME)) return;

        for(Generator generator : BedwarsPlugin.instance.mapManager.getLoadedMap().generators()) {
            generator.tick();
        }
        scoreboard.updateData(scoreboardData);
    }

    public void handleBlockPlace(BlockPlaceEvent event) {
        placedBlocks.add(event.getBlockPlaced());
    }

    public void handleBlockBreak(BlockBreakEvent event) {

        if(!gameState.equals(GameState.INGAME)) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();
        if(placedBlocks.contains(block)) {
            placedBlocks.remove(block);
            return;
        } else {
            for(GameTeam team : game.getTeams()) {
                if(team.isBedLocation(block.getLocation())) {
                    team.breakBed(event.getPlayer());
                    return;
                }
            }
        }
        event.setCancelled(true);
    }

    public void reset() {
        gameState = GameState.POSTLOBBY;
        busy = true;
    }


    //GETTERS
    public GameState getGameState() {
        return gameState;
    }

    public boolean allowJoin() {
        return gameState.equals(GameState.LOBBY);
    }

    public GameTeam getPlayerTeam(Player player) {
        for(GameTeam team : game.getTeams()) {
            if(team.hasMember(player)) return team;
        }
        return null;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<GameShop> getShops() {
        return shops;
    }
}
