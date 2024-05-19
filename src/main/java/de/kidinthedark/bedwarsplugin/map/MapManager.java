package de.kidinthedark.bedwarsplugin.map;

import java.util.HashMap;

public class MapManager {

    private MapState mapState = MapState.PREPARING;

    private final HashMap<String, Map> maps;

    public MapManager() {
        maps = new HashMap<>();
    }

    public boolean isReady() {
        return mapState.equals(MapState.READY);
    }

    public void prepare() {
        if (!mapState.equals(MapState.PREPARING)) return;
    }

}
