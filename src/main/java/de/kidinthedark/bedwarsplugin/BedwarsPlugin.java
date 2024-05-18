package de.kidinthedark.bedwarsplugin;

import de.kidinthedark.bedwarsplugin.game.GameManager;
import de.kidinthedark.bedwarsplugin.game.UniversalSecondsGameTimer;
import de.kidinthedark.bedwarsplugin.util.ConfigLoader;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguageLoader;
import de.kidinthedark.bedwarsplugin.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedwarsPlugin extends JavaPlugin {

    public static BedwarsPlugin instance;

    public MySQL mySQL;

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

        getLogger().info("Connecting mysql...");
        mySQL = new MySQL(ConfigVars.mysqlHost, ConfigVars.mysqlPort + "", ConfigVars.mysqlDatabase, ConfigVars.mysqlUser, ConfigVars.mysqlPassword, ConfigVars.useSSL, ConfigVars.autoReconnect, ConfigVars.allowPublicKeyRetrieval);
        mySQL.connect();
        createDefaultMysqlTables();

        UniversalSecondsGameTimer.startTimer();
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
