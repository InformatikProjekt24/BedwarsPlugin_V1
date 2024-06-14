package de.kidinthedark.bedwarsplugin.util;

import org.bukkit.Location;

import java.util.List;

public class ConfigVars {

    /* Utility for multilingual support */
    public static List<String> availableLanguages;
    public static List<String> languageMessages;
    public static String defaultLanguage;

    public static Location lobbySpawnLocation;
    public static int maxPlayers;
    public static int playersRequired;

    public static List<String> mapsAvailable;

    public static String mysqlUser;
    public static String mysqlPassword;
    public static String mysqlDatabase;
    public static String mysqlHost;
    public static int mysqlPort;
    public static boolean allowPublicKeyRetrieval;
    public static boolean autoReconnect;
    public static boolean useSSL;

    public static String prefix;

    public static int lobbycountdown;
    public static int pregamecountdown;
    public static int postgamecountdown;

    //TODO READ CONFIG FILE
    public static int scoreboardLines;
    public static String scoreboardTitle;

}
