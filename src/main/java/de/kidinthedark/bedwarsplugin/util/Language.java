package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;

import java.util.HashMap;

public class Language {

    private final String locale;
    private final FileBuilder file;

    private HashMap<String, String> messsages;

    public Language(String locale, FileBuilder file) {
        this.locale = locale;
        this.file = file;
    }

    public String getLocale() {
        return locale;
    }

    public boolean load() {
        messsages = new HashMap<>();
        boolean missingElements = false;

        for(String message : ConfigVars.languageMessages) {
            if(file.getString(message) == null) {
                missingElements = true;
                BedwarsPlugin.instance.getLogger().warning("[LanguageLoader] missing " + message + " in language pack " + locale);
            } else {
                BedwarsPlugin.instance.getLogger().info("[LanguageLoader] " + locale + "_" + message + " is " + file.getString(message));
                messsages.put(message, file.getString(message));
            }
        }

        if(missingElements) {
            BedwarsPlugin.instance.getLogger().warning("[LanguageLoader] Did not load language pack " + locale + " due to missing elements!");
        }
        return !missingElements;
    }

    public String getMessage(String messageName) {
        return messsages.getOrDefault(messageName, "");
    }

}
