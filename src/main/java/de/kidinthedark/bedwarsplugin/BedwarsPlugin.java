package de.kidinthedark.bedwarsplugin;

import de.kidinthedark.bedwarsplugin.game.GameManager;
import de.kidinthedark.bedwarsplugin.game.UniversalSecondsGameTimer;
import de.kidinthedark.bedwarsplugin.util.ConfigLoader;
import de.kidinthedark.bedwarsplugin.util.LanguageLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedwarsPlugin extends JavaPlugin {

    public static BedwarsPlugin instance;

    public ConfigLoader configLoader;
    public GameManager gameManager;
    public LanguageLoader languageLoader;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Loading plugin...");
        getLogger().info("Loading config files...");
        configLoader = new ConfigLoader();
        configLoader.loadConfig();
        getLogger().info("Config loaded!");

        languageLoader = new LanguageLoader();
        languageLoader.loadLanguages();

        getLogger().info("Loading Gamemanager...");
        gameManager = new GameManager();
        gameManager.prepareServer();
        getLogger().info("Gamemanager loaded!");

        UniversalSecondsGameTimer.startTimer();
    }

    @Override
    public void onDisable() {
        UniversalSecondsGameTimer.stopTimer();
        // Plugin shutdown logic
    }
}
