package de.kidinthedark.bedwarsplugin.map;

import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.WorldManager;

import java.io.IOException;
import java.util.HashMap;

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
    }

}
