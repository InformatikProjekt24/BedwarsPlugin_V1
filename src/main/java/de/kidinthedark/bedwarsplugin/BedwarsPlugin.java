package de.kidinthedark.bedwarsplugin;

import de.kidinthedark.bedwarsplugin.game.GameManager;
import de.kidinthedark.bedwarsplugin.game.UniversalSecondsGameTimer;
import de.kidinthedark.bedwarsplugin.listeners.LoginListener;
import de.kidinthedark.bedwarsplugin.util.ConfigLoader;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguageLoader;
import de.kidinthedark.bedwarsplugin.util.MySQL;
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

        getLogger().info("Loading GameManager...");
        gameManager = new GameManager();
        gameManager.prepareServer();
        getLogger().info("GameManager loaded!");

        getLogger().info("Connecting MySQL...");
        mySQL = new MySQL(ConfigVars.mysqlHost, ConfigVars.mysqlPort + "", ConfigVars.mysqlDatabase, ConfigVars.mysqlUser, ConfigVars.mysqlPassword, ConfigVars.useSSL, ConfigVars.autoReconnect, ConfigVars.allowPublicKeyRetrieval);
        mySQL.connect();
        createDefaultMysqlTables();


        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new LoginListener(), instance);
        getLogger().info("Listeners registered!");

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
