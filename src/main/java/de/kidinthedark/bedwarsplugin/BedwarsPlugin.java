package de.kidinthedark.bedwarsplugin;

import de.kidinthedark.bedwarsplugin.game.GameManager;
import de.kidinthedark.bedwarsplugin.game.UniversalSecondsGameTimer;
import de.kidinthedark.bedwarsplugin.util.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedwarsPlugin extends JavaPlugin {

    public static BedwarsPlugin instance;

    public ConfigLoader configLoader;
    public GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Loading plugin...");
        getLogger().info("Loading config files...");
        configLoader = new ConfigLoader();
        configLoader.loadConfig();
        getLogger().info("Config loaded!");


        UniversalSecondsGameTimer.startTimer();
    }

    @Override
    public void onDisable() {
        UniversalSecondsGameTimer.stopTimer();
        // Plugin shutdown logic
    }
}
