package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.game.GameTeam;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.FileBuilder;
import de.kidinthedark.bedwarsplugin.util.WorldManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MapManager {

    private MapState mapState = MapState.PREPARING;

    private Map loadedMap;
    private final WorldManager worldManager;

    public MapManager() {
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

    public Map getLoadedMap() {
        return loadedMap;
    }

    public boolean prepare() {
        if (!mapState.equals(MapState.PREPARING)) return false;
        Random random = new Random();
        if (ConfigVars.mapsAvailable.isEmpty()) return true;
        int index = random.nextInt(ConfigVars.mapsAvailable.size());
        String mapName = ConfigVars.mapsAvailable.get(index);
        ArrayList<GameTeam> teams = new ArrayList<>();
        ArrayList<Generator> generators = new ArrayList<>();

        FileBuilder mapData = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath() + "/savedMaps", mapName + ".yml");

        String mapDisplayName = mapData.getString("mapName");

        if(!loadMap(mapName)) {
            Bukkit.shutdown();
            return false;
        }

        mapName += "_playable";

        World world = BedwarsPlugin.instance.getServer().getWorld(mapName);

        for (String key : mapData.getConfigurationSection("teams").getKeys(false)) {
            String teamColour = mapData.getString(key + ".colour");
            double xSpawn = mapData.getDouble(key + ".spawn.x");
            double ySpawn = mapData.getDouble(key + ".spawn.y");
            double zSpawn = mapData.getDouble(key + ".spawn.z");
            double yawSpawn = mapData.getDouble(key + ".spawn.yaw");
            double pitSpawn = mapData.getDouble(key + ".spawn.pit");

            int x1Bed = mapData.getInt(key + ".bed.x1");
            int y1Bed = mapData.getInt(key + ".bed.y1");
            int z1Bed = mapData.getInt(key + ".bed.z1");
            int x2Bed = mapData.getInt(key + ".bed.x2");
            int y2Bed = mapData.getInt(key + ".bed.y2");
            int z2Bed = mapData.getInt(key + ".bed.z2");

            double xTeamShop = mapData.getDouble(key + ".team_shop.x");
            double yTeamShop = mapData.getDouble(key + ".team_shop.y");
            double zTeamShop = mapData.getDouble(key + ".team_shop.z");
            double xUpgradeShop = mapData.getDouble(key + ".upgrade_shop.x");
            double yUpgradeShop = mapData.getDouble(key + ".upgrade_shop.y");
            double zUpgradeShop = mapData.getDouble(key + ".upgrade_shop.z");


            Location spawnLocation = new Location(world, xSpawn, ySpawn, zSpawn, (float) yawSpawn, (float) pitSpawn);
            Location bed1Location = new Location(world, x1Bed, y1Bed, z1Bed);
            Location bed2Location = new Location(world, x2Bed, y2Bed, z2Bed);
            Location teamShopLocation = new Location(world, xTeamShop, yTeamShop, zTeamShop);
            Location upgradeShopLocation = new Location(world, xUpgradeShop, yUpgradeShop, zUpgradeShop);

            GameTeam team = switch (teamColour) {
                case "RED" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.RED);
                case "WHITE" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.WHITE);
                case "AQUA" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.AQUA);
                case "YELLOW" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.YELLOW);
                case "GREEN" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.GREEN);
                case "BLUE" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.BLUE);
                case "PINK" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.PURPLE);
                case "GRAY" -> new GameTeam(spawnLocation, bed1Location, bed2Location, teamShopLocation, upgradeShopLocation, Color.GRAY);
                default -> null;
            };

            if (team == null) {
                BedwarsPlugin.instance.getLogger().severe("ERROR: Could not parse colour!");
                Bukkit.shutdown();
                return false;
            }

            teams.add(team);
        }

        for (String key : mapData.getConfigurationSection("generators").getKeys(false)) {
            double xGen = mapData.getDouble(key + ".x");
            double yGen = mapData.getDouble(key + ".y");
            double zGen = mapData.getDouble(key + ".z");

            Location genLocation = new Location(world, xGen, yGen, zGen);

            String genType = mapData.getString(key + ".type");

            Generator generator = switch (genType) {
                case "GOLD" -> new Generator(genLocation, Material.GOLD_INGOT, 8);
                case "DIAMOND" -> new Generator(genLocation, Material.DIAMOND, 30);
                case "EMERALD" -> new Generator(genLocation, Material.EMERALD, 65);
                case "IRON" -> new Generator(genLocation, Material.IRON_INGOT, 2);
                default -> null;
            };

            if (generator == null) {
                BedwarsPlugin.instance.getLogger().severe("ERROR: Could not parse generator material!");
                Bukkit.shutdown();
                return false;
            }

            generators.add(generator);
        }

        loadedMap = new Map(mapName.replaceAll("_preparing", ""), mapDisplayName, teams, generators);

        mapState = MapState.READY;
        return true;
    }

    public void reset() {
        mapState = MapState.RESET_PENDING;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().getName().equals(loadedMap.name())) {
                player.teleport(ConfigVars.lobbySpawnLocation);
            }
        }

        worldManager.unloadWorld(loadedMap.name());

        mapState = MapState.END;
    }

}
