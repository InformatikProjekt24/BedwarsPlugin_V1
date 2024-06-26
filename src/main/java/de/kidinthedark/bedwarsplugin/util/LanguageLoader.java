package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LanguageLoader {

    private final HashMap<String, Language> loadedLanguages;
    private final HashMap<String, String> playerLanguages;

    public LanguageLoader() {
        loadedLanguages = new HashMap<>();
        playerLanguages = new HashMap<>();
    }

    public void loadLanguages() {

        BedwarsPlugin.instance.getLogger().info("[LanguageLoader] Loading languages...");
        loadedLanguages.clear();
        if (!ConfigVars.availableLanguages.contains(ConfigVars.defaultLanguage)) {
            BedwarsPlugin.instance.getLogger().severe("[LanguageLoader] " + ConfigVars.defaultLanguage + " is the default language pack. Do not use the Server without it as errors will occur!");
        } else {
            BedwarsPlugin.instance.getLogger().info("[LanguageLoader] " + ConfigVars.defaultLanguage + " is the default language pack.");
        }

        if(!ConfigVars.availableLanguages.contains(ConfigVars.defaultLanguage)) {
            String nextDefault = ConfigVars.availableLanguages.getFirst();
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
                        assert in != null;
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

        if(!loadedLanguages.containsKey(ConfigVars.defaultLanguage)) {
            BedwarsPlugin.instance.getLogger().severe("[LanguageLoader] The default language pack could not be loaded! Shutting down...");
            Bukkit.shutdown();
        }
    }

    public String getMessage(String locale, String messageName) {
        return loadedLanguages.getOrDefault(locale, loadedLanguages.get(ConfigVars.defaultLanguage)).getMessage(messageName);
    }

    public String getPlayerLanguage(Player p) {
        if(playerLanguages.containsKey(p.getUniqueId().toString())) {
            return playerLanguages.get(p.getUniqueId().toString());
        }

        String lang = ConfigVars.defaultLanguage;

        if(BedwarsPlugin.instance.mySQL.isConnected()) {
            ResultSet rs = BedwarsPlugin.instance.mySQL.getResult("SELECT lang FROM playerLanguages WHERE uuid='"+p.getUniqueId()+"'");
            try {
                if(rs.next()) {
                    lang = rs.getString("lang");
                    playerLanguages.put(p.getUniqueId().toString(), lang);
                } else {
                    BedwarsPlugin.instance.mySQL.update("INSERT INTO playerLanguages (uuid, lang) VALUES ('"+p.getUniqueId()+"','"+lang+"')");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return lang;
    }

    public void setPlayerLanguage(Player p, String lang) {
        playerLanguages.replace(p.getUniqueId().toString(), lang);

        if(!getPlayerLanguage(p).equals(lang)) {
            BedwarsPlugin.instance.mySQL.update("UPDATE playerLanguages SET lang='"+lang+"' WHERE uuid='"+p.getUniqueId()+"'");
        }
    }

    public List<String> getLocales() {
        return loadedLanguages.keySet().stream().toList();
    }

}
