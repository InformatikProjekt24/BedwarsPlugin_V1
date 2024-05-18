package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigLoader {

    private FileBuilder configFile;


    public ConfigLoader() {
        //TODO FINISH
    }

    public void loadConfig() {
        if (!BedwarsPlugin.instance.getDataFolder().exists())
            BedwarsPlugin.instance.getDataFolder().mkdir();

        File file = new File(BedwarsPlugin.instance.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = BedwarsPlugin.instance.getResource("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        configFile = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath().toString(), "config.yml");

        /* Load the config */
        ConfigVars.defaultLanguage = configFile.getString("defaultLanguage");
        ConfigVars.availableLanguages = configFile.getSringList("langs");
        ConfigVars.languageMessages = configFile.getSringList("messages");
        ConfigVars.lobbycountdown = configFile.getInt("lobbycountdown");
        ConfigVars.pregamecountdown = configFile.getInt("pregamecountdown");
        ConfigVars.postgamecountdown = configFile.getInt("postgamecountdown");
    }

    public void saveConfig() {

    }

}
