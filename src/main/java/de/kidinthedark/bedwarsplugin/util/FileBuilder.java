package de.kidinthedark.bedwarsplugin.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileBuilder {

    private final YamlConfiguration c;
    private final File f;

    public boolean exist() {
        return f.exists();
    }

    public FileBuilder(String FilePath, String FileName) {
        this.f = new File(FilePath, FileName);
        this.c = YamlConfiguration.loadConfiguration(this.f);
    }
    public void mkfile() {
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete() {
        return f.delete();
    }

    public void addDefault(String path, Object value) {
        c.addDefault(path, value);
    }

    public void copyDefaults(boolean def) {
        c.options().copyDefaults(def);
    }

    public FileBuilder setValue(String ValuePath, Object Value) {
        c.set(ValuePath, Value);
        return this;
    }

    public int getInt(String ValuePath) {
        return c.getInt(ValuePath);
    }
    public String getString(String ValuePath) {
        return c.getString(ValuePath);
    }
    public long getLong(String ValuePath) {
        return c.getLong(ValuePath);
    }
    public double getDouble(String ValuePath) {
        return c.getDouble(ValuePath);
    }
    public boolean getBoolean(String ValuePath) {
        return c.getBoolean(ValuePath);
    }
    public List<String> getSringList(String ValuePath) {
        return c.getStringList(ValuePath);
    }

    public Set<String> getKeys(boolean deep){
        return c.getKeys(deep);
    }

    public ConfigurationSection getConfigurationSection(String Section) {
        return c.getConfigurationSection(Section);
    }

    public FileBuilder save() {
        try {
            this.c.save(this.f);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return this;
    }
}
