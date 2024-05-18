package de.kidinthedark.bedwarsplugin.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.kidinthedark.bedwarsplugin.BedwarsPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigLoader {

    private FileBuilder configFile;


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

        configFile = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath().toString(), "config.yml");

        /* Load the config */
        ConfigVars.mysqlUser = configFile.getString("mysql.user");
        ConfigVars.mysqlPassword = configFile.getString("mysql.password");
        ConfigVars.mysqlDatabase = configFile.getString("mysql.database");
        ConfigVars.mysqlHost = configFile.getString("mysql.host");
        ConfigVars.mysqlPort = configFile.getInt("mysql.port");
        ConfigVars.allowPublicKeyRetrieval = configFile.getBoolean("allowPublicKeyRetrieval");
        ConfigVars.autoReconnect = configFile.getBoolean("autoReconnect");
        ConfigVars.useSSL = configFile.getBoolean("useSSL");

        ConfigVars.mapsAvailable = configFile.getSringList("maps");

        ConfigVars.prefix = configFile.getString("prefix");
        ConfigVars.defaultLanguage = configFile.getString("defaultLanguage");
        ConfigVars.availableLanguages = configFile.getSringList("langs");
        ConfigVars.languageMessages = configFile.getSringList("messages");
        ConfigVars.lobbycountdown = configFile.getInt("lobbycountdown");
        ConfigVars.pregamecountdown = configFile.getInt("pregamecountdown");
        ConfigVars.postgamecountdown = configFile.getInt("postgamecountdown");
    }

}
