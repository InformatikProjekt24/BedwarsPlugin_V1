package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageFactory {

    public static void sendMessage(String message, LanguagePlaceholder placeholders, Player receiver) {
        String lang = BedwarsPlugin.instance.languageLoader.getPlayerLanguage(receiver);
        String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
        receiver.sendMessage(ConfigVars.prefix + " " + placeholders.replacePlaceholders(messageToParse));
    }

    public static void broadcastMessage(String message, LanguagePlaceholder placeholders, Player exclude) {
        for(Player receiver : Bukkit.getOnlinePlayers()) {
            if(receiver.getUniqueId().equals(exclude.getUniqueId())) continue;
            String lang = BedwarsPlugin.instance.languageLoader.getPlayerLanguage(receiver);
            String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
            receiver.sendMessage(ConfigVars.prefix + " " + placeholders.replacePlaceholders(messageToParse));
        }
    }

    public static void broadcastMessage(String message, LanguagePlaceholder placeholders) {
        for(Player receiver : Bukkit.getOnlinePlayers()) {
            String lang = BedwarsPlugin.instance.languageLoader.getPlayerLanguage(receiver);
            String messageToParse = BedwarsPlugin.instance.languageLoader.getMessage(lang, message);
            receiver.sendMessage(ConfigVars.prefix + " " + placeholders.replacePlaceholders(messageToParse));
        }
    }

}
