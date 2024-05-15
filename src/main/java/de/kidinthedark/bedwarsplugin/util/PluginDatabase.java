package de.kidinthedark.bhwmcplugin.util;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;

import java.text.SimpleDateFormat;
import java.util.List;

public class PluginDatabase {

    private final FileBuilder config;
    private FileBuilder banlist;
    private final FileBuilder cachedPlayers;

    public PluginDatabase() {
        config = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "config.yml");
        banlist = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "banlist.yml");
        cachedPlayers = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "cachedPlayers.yml");
    }

    public boolean isMaintenance() {
        return config.getBoolean("maintenance");
    }

    public void setMaintenance(boolean maint) {
        config.setValue("maintenance", maint);
        config.save();
    }

    public boolean isPlayerCached(String uuid) {
        return cachedPlayers.getSringList("players").contains(uuid);
    }

    public CachedPlayer getCachedPlayer(String uuid) {
        if(isPlayerCached(uuid)) {
            FileBuilder playerInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "/playercache", uuid + ".yml");

            return new CachedPlayer(uuid, playerInfo.getString("name"), playerInfo.getLong("lastSeen"), playerInfo.getString("address"));
        }
        return null;
    }

    public void setCachedPlayer(CachedPlayer player) {
        List<String> players = cachedPlayers.getSringList("players");
        if(!players.contains(player.getUuid())) {
            players.add(player.getUuid());
            cachedPlayers.setValue("players", players);
            cachedPlayers.save();
        }
        FileBuilder playerInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "/playercache", player.getUuid() + ".yml");

        if(!playerInfo.exist()) playerInfo.mkfile();

        playerInfo.setValue("uuid", player.getUuid());
        playerInfo.setValue("name", player.getName());
        playerInfo.setValue("lastSeen", player.getLastSeen());
        playerInfo.setValue("address", player.getAddress());
        playerInfo.save();
    }

    public FileBuilder isPlayerBanned(String uuid) {
        banlist = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "banlist.yml");
        if(banlist.getSringList("banlist").contains(uuid)) {
            FileBuilder banInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "/bans", uuid + ".yml");

            if(banInfo.getLong("duration") == -1) return banInfo;

            if(banInfo.getLong("expires") < System.currentTimeMillis()) {
                banInfo.delete();
                List<String> bans = banlist.getSringList("banlist");
                bans.remove(uuid);
                banlist.setValue("banlist", bans);
                banlist.save();
                return null;
            }

            return banInfo;
        } else {
            return null;
        }
    }

    public void setPlayerBanned(CachedPlayer player, String reason, long duration) {
        String uuid = player.getUuid();
        FileBuilder banInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "\\bans", uuid + ".yml");

        banInfo.setValue("duration", duration);
        banInfo.setValue("reason", reason);
        banInfo.setValue("expires", System.currentTimeMillis() + duration);
        banInfo.save();

        List<String> bans = banlist.getSringList("banlist");
        bans.add(uuid);
        banlist.setValue("banlist", bans);
        banlist.save();

        OfflinePlayer p = Bukkit.getOfflinePlayer(player.getName());
        if(p.isOnline()) {
            if(duration == -1) {
                ((Player) p).kick(Component.text("§cDu wurdest gebannt!\n\n§eGrund: §7" + reason + "\n§eEntbannungsdatum: §cPERMANENT" ), PlayerKickEvent.Cause.BANNED);
            } else {
                ((Player) p).kick(Component.text("§cDu wurdest gebannt!\n\n§eGrund: §7" + reason + "\n§eEntbannungsdatum: §7" + new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(banInfo.getLong("expires"))), PlayerKickEvent.Cause.BANNED);
            }
        }

    }

}
