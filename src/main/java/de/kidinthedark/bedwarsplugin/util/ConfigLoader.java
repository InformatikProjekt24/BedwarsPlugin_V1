package de.kidinthedark.bedwarsplugin.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


@SuppressWarnings({"ResultOfMethodCallIgnored", "deprecation"})
public class ConfigLoader {


    public ConfigLoader() {

    }

    @CanIgnoreReturnValue
    public void loadConfig() {
        if (!BedwarsPlugin.instance.getDataFolder().exists()) {
            BedwarsPlugin.instance.getDataFolder().mkdir();
        }


        File file = new File(BedwarsPlugin.instance.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = BedwarsPlugin.instance.getResource("config.yml")) {
                assert in != null;
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileBuilder configFile = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath().toString(), "config.yml");

        /* Load the config */
        //MySQL
        ConfigVars.mysqlUser = configFile.getString("mysql.user");
        ConfigVars.mysqlPassword = configFile.getString("mysql.password");
        ConfigVars.mysqlDatabase = configFile.getString("mysql.database");
        ConfigVars.mysqlHost = configFile.getString("mysql.host");
        ConfigVars.mysqlPort = configFile.getInt("mysql.port");
        ConfigVars.allowPublicKeyRetrieval = configFile.getBoolean("allowPublicKeyRetrieval");
        ConfigVars.autoReconnect = configFile.getBoolean("autoReconnect");
        ConfigVars.useSSL = configFile.getBoolean("useSSL");

        //Map loading
        ConfigVars.mapsAvailable = configFile.getStringList("maps");
        String lobbyWorld = configFile.getString("lobby.world");
        double lobbyX = configFile.getDouble("lobby.x");
        double lobbyY = configFile.getDouble("lobby.y");
        double lobbyZ = configFile.getDouble("lobby.z");
        float yaw = (float) configFile.getDouble("yaw");
        float pitch = (float) configFile.getDouble("pit");
        ConfigVars.lobbySpawnLocation = new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ, yaw, pitch);

        ConfigVars.playersRequired = configFile.getInt("lobbystartthreshold");
        ConfigVars.maxPlayers = configFile.getInt("maxplayers");

        ConfigVars.prefix = ChatColor.translateAlternateColorCodes('&', configFile.getString("prefix"));
        ConfigVars.defaultLanguage = configFile.getString("defaultLanguage");
        ConfigVars.availableLanguages = configFile.getStringList("langs");
        ConfigVars.languageMessages = configFile.getStringList("messages");
        ConfigVars.lobbycountdown = configFile.getInt("lobbycountdown");
        ConfigVars.pregamecountdown = configFile.getInt("pregamecountdown");
        ConfigVars.postgamecountdown = configFile.getInt("postgamecountdown");
    }

    public void unloadConfig() {
        FileBuilder configFile = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath().toString(), "config.yml");
        configFile.save();
    }

}
