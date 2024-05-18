package de.kidinthedark.bedwarsplugin.util;

import java.util.HashMap;

public class LanguagePlaceholder {

    private HashMap<String, Object> placeholders;

    public LanguagePlaceholder() {
        placeholders = new HashMap<>();
    }

    public void updatePlaceholder(String placeholderName, Object value) {
        placeholders.putIfAbsent(placeholderName, value);
        placeholders.replace(placeholderName, value);
    }

    public String replacePlaceholders(String message) {
        for(String placeholder : placeholders.keySet()) {
            message = message.replaceAll("{" + placeholder + "}", placeholders.get("placeholder").toString());
        }
        return message;
    }

}
