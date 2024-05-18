package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class LanguageLoader {

    private HashMap<String, Language> loadedLanguages;

    public LanguageLoader() {
        loadedLanguages = new HashMap<>();
    }

    public void loadLanguages() {

        BedwarsPlugin.instance.getLogger().info("[LanguageLoader] Loading languages...");

        if (!ConfigVars.availableLanguages.contains(ConfigVars.defaultLanguage)) {
            BedwarsPlugin.instance.getLogger().severe("[LanguageLoader] " + ConfigVars.defaultLanguage + " is the default language pack. Do not use the Server without it as errors will occur!");
        } else {
            BedwarsPlugin.instance.getLogger().info("[LanguageLoader] " + ConfigVars.defaultLanguage + " is the default language pack.");
        }

        if(!ConfigVars.availableLanguages.contains(ConfigVars.defaultLanguage)) {
            String nextDefault = ConfigVars.availableLanguages.get(0);
            BedwarsPlugin.instance.getLogger().severe("[LanguageLoader] Selected default language " + ConfigVars.defaultLanguage + " is not available, defaulting to " + nextDefault);
            ConfigVars.defaultLanguage = nextDefault;
        }

        for(String lang : ConfigVars.availableLanguages) {
            BedwarsPlugin.instance.getLogger().info("[LanguageLoader] Loading " + lang);

            File fileFolder = new File(BedwarsPlugin.instance.getDataFolder(), "lang");
            fileFolder.mkdir();

            File file = new File(BedwarsPlugin.instance.getDataFolder() + "/lang", lang + ".yml");

            if (!file.exists()) {
                if(BedwarsPlugin.instance.getResource("lang/" + lang + ".yml") != null) {
                    try (InputStream in = BedwarsPlugin.instance.getResource("lang/" + lang + ".yml")) {
                        Files.copy(in, file.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            FileBuilder langFile = new FileBuilder(BedwarsPlugin.instance.getDataFolder() + "/lang", lang + ".yml");

            Language language = new Language(lang, langFile);
            if(language.load()) {
                loadedLanguages.put(lang, language);
                BedwarsPlugin.instance.getLogger().info("[LanguageLoader] Loaded " + lang);
            }

        }
    }

    public String getMessage(String locale, String messageName) {
        return loadedLanguages.getOrDefault(locale, loadedLanguages.get("en_UK")).getMessage(messageName);
    }

}
