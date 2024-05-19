package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageFactory {

    public static void sendMessage(String message, LanguagePlaceholder placeholders, Player receiver) {
        String lang = BedwarsPlugin.instance.languageLoader.getPlayerLanguage(receiver);
        String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
        receiver.sendMessage(ConfigVars.prefix + " " + placeholders.replacePlaceholders(lang, messageToParse));
    }

    public static void broadcastMessage(String message, LanguagePlaceholder placeholders, Player exclude) {
        for(Player receiver : Bukkit.getOnlinePlayers()) {
            if(receiver.getUniqueId().equals(exclude.getUniqueId())) continue;
            sendMessage(message, placeholders, receiver);
        }
        BedwarsPlugin.instance.getLogger().info(ConfigVars.prefix + " " + getMessage(message, placeholders, ConfigVars.defaultLanguage));
    }

    public static void broadcastMessage(String message, LanguagePlaceholder placeholders) {
        for(Player receiver : Bukkit.getOnlinePlayers()) {
            sendMessage(message, placeholders, receiver);
        }
        BedwarsPlugin.instance.getLogger().info(ConfigVars.prefix + " " + getMessage(message, placeholders, ConfigVars.defaultLanguage));
    }

    public static String getMessage(String message, LanguagePlaceholder placeholders, Player receiver) {
        String lang = BedwarsPlugin.instance.languageLoader.getPlayerLanguage(receiver);
        String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
        return placeholders.replacePlaceholders(lang, messageToParse);
    }

    public static String getMessage(String message, LanguagePlaceholder placeholders, String lang) {
        String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
        return placeholders.replacePlaceholders(lang, messageToParse);
    }

    public static String getTimeUnit(String locale, String timeUnit) {
        return BedwarsPlugin.instance.languageLoader.getMessage(locale, timeUnit);
    }

}
