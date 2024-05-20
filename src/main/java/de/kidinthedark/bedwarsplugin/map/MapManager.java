package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.game.GameTeam;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.FileBuilder;
import de.kidinthedark.bedwarsplugin.util.WorldManager;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapManager {

    private MapState mapState = MapState.PREPARING;

    private final HashMap<String, Map> maps;
    private final WorldManager worldManager;

    public MapManager() {
        maps = new HashMap<>();
        worldManager = new WorldManager();
    }

    public boolean loadMap(String mapName) {
        if(ConfigVars.mapsAvailable.contains(mapName)) {
            try {
                worldManager.loadWorld(mapName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isReady() {
        return mapState.equals(MapState.READY);
    }

    public void prepare() {
        if (!mapState.equals(MapState.PREPARING)) return;
        Random random = new Random();
        int index = random.nextInt(ConfigVars.mapsAvailable.size());
        String mapName = ConfigVars.mapsAvailable.get(index);

        FileBuilder mapData = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath() + "/savedMaps", mapName + ".yml");

        String mapDisplayName = mapData.getString("mapName");

        loadMap(mapName);
        mapName += "_playable";

        ArrayList<GameTeam> teams = new ArrayList<>();

        for (String key : mapData.getConfigurationSection("teams").getKeys(false)) {
            String teamColour = mapData.getString(key + ".colour");
            double xSpawn = mapData.getDouble(key + "spawn.x");
            double ySpawn = mapData.getDouble(key + "spawn.y");
            double zSpawn = mapData.getDouble(key + "spawn.z");
            double yawSpawn = mapData.getDouble(key + "spawn.yaw");
            double pitSpawn = mapData.getDouble(key + "spawn.pit");

            int x1Bed = mapData.getInt(key + "bed.x1");
            int y1Bed = mapData.getInt(key + "bed.y1");
            int z1Bed = mapData.getInt(key + "bed.z1");
            int x2Bed = mapData.getInt(key + "bed.x2");
            int y2Bed = mapData.getInt(key + "bed.y2");
            int z2Bed = mapData.getInt(key + "bed.z2");
            
            World world = BedwarsPlugin.instance.getServer().getWorld(mapName);
            Location spawnLocation = new Location(world, xSpawn, ySpawn, zSpawn, (float) yawSpawn, (float) pitSpawn);
            Location bed1Location = new Location(world, x1Bed, y1Bed, z1Bed);
            Location bed2Location = new Location(world, x2Bed, y2Bed, z2Bed);

            switch(teamColour) {
                case "RED":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.RED);
                    break;
                case "WHITE":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.WHITE);
                    break;
                case "AQUA":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.AQUA);
                    break;
                case "YELLOW":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.YELLOW);
                    break;
                case "GREEN":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.GREEN);
                    break;
                case "BLUE":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.BLUE);
                    break;
                case "PINK":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.PURPLE);
                    break;
                case "GRAY":
                    GameTeam team = new GameTeam(spawnLocation, bed1Location, bed2Location, Color.GRAY);
                    break;
            }

            teams.add(team);
        }

    }

}
