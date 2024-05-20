package de.kidinthedark.bedwarsplugin;

import de.kidinthedark.bedwarsplugin.commands.LanguageCommand;
import de.kidinthedark.bedwarsplugin.commands.StartCommand;
import de.kidinthedark.bedwarsplugin.commands.tabcompleter.LanguageCommandCompleter;
import de.kidinthedark.bedwarsplugin.commands.tabcompleter.StartCommandCompleter;
import de.kidinthedark.bedwarsplugin.game.GameManager;
import de.kidinthedark.bedwarsplugin.game.UniversalSecondsGameTimer;
import de.kidinthedark.bedwarsplugin.listeners.JoinListener;
import de.kidinthedark.bedwarsplugin.listeners.LoginListener;
import de.kidinthedark.bedwarsplugin.map.MapManager;
import de.kidinthedark.bedwarsplugin.util.ConfigLoader;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguageLoader;
import de.kidinthedark.bedwarsplugin.util.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class BedwarsPlugin extends JavaPlugin {

    public static BedwarsPlugin instance;

    public MySQL mySQL;

    public ConfigLoader configLoader;
    public GameManager gameManager;
    public LanguageLoader languageLoader;
    public MapManager mapManager;

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

        getLogger().info("Loading GameManager...");
        gameManager = new GameManager();
        gameManager.prepareServer();
        getLogger().info("GameManager loaded!");

        getLogger().info("Loading MapManager...");
        mapManager = new MapManager();
        if(mapManager.prepare()) {
            getLogger().info("MapManager loaded!");
        } else {
            getLogger().severe("Error loading MapManager!");
        }

        getLogger().info("Connecting MySQL...");
        mySQL = new MySQL(ConfigVars.mysqlHost, ConfigVars.mysqlPort + "", ConfigVars.mysqlDatabase, ConfigVars.mysqlUser, ConfigVars.mysqlPassword, ConfigVars.useSSL, ConfigVars.autoReconnect, ConfigVars.allowPublicKeyRetrieval);
        mySQL.connect();
        createDefaultMysqlTables();

        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new LoginListener(), instance);
        getServer().getPluginManager().registerEvents(new JoinListener(), instance);
        getLogger().info("Listeners registered!");

        getLogger().info("Registering commands...");
        Objects.requireNonNull(getCommand("start")).setExecutor(new StartCommand());
        Objects.requireNonNull(getCommand("language")).setExecutor(new LanguageCommand());

        Objects.requireNonNull(getCommand("start")).setTabCompleter(new StartCommandCompleter());
        Objects.requireNonNull(getCommand("language")).setTabCompleter(new LanguageCommandCompleter());
        getLogger().info("Commands registered!");

        UniversalSecondsGameTimer.startTimer();

        getLogger().info("Plugin loaded!");
    }

    @Override
    public void onDisable() {
        UniversalSecondsGameTimer.stopTimer();
        // Plugin shutdown logic
    }

    public void createDefaultMysqlTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS playerLanguages (uuid VARCHAR(100), lang VARCHAR(10));");
    }
}
