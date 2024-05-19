package de.kidinthedark.bedwarsplugin.util;

import org.bukkit.ChatColor;

import java.util.HashMap;

@SuppressWarnings("deprecation")
public class LanguagePlaceholder {

    private final HashMap<String, Object> placeholders;

    public LanguagePlaceholder() {
        placeholders = new HashMap<>();
    }

    public void updatePlaceholder(String placeholderName, Object value) {
        placeholders.putIfAbsent(placeholderName, value);
        placeholders.replace(placeholderName, value);
    }

    public String replacePlaceholders(String locale, String message) {

        for(String placeholder : placeholders.keySet()) {

            if(placeholder.equals("timeUnit")) {
                message = message.replaceAll("\\{" + placeholder + "}", MessageFactory.getTimeUnit(locale, placeholders.get(placeholder).toString()));
                continue;
            }

            message = message.replaceAll("\\{" + placeholder + "}", placeholders.get(placeholder).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
