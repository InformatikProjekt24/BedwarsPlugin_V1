package de.kidinthedark.bhwmcplugin.util;

import org.bukkit.Bukkit;

public class CachedPlayer {

    private final String uuid;
    private final String name;
    private final String address;
    private long lastSeen;

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public boolean isOnline() {
        return Bukkit.getOfflinePlayer(name).isOnline();
    }

    public CachedPlayer(String uuid, String name, long lastSeen, String ip) {
        this.uuid = uuid.replaceAll("\"", "");
        this.name = name;
        this.address = ip;
        this.lastSeen = lastSeen;
    }

}
