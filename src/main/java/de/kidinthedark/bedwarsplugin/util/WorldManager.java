package de.kidinthedark.bedwarsplugin.util;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldManager {

    public void loadWorld(String worldName) throws IOException {
        BedwarsPlugin.instance.getLogger().info("[WorldManager] Loading world " + worldName + "_playable...");
        copyDirectory(BedwarsPlugin.instance.getDataFolder() + "/savedMaps/" + worldName, worldName + "_playable");
        BedwarsPlugin.instance.getServer().createWorld(WorldCreator.name(worldName + "_playable"));
        BedwarsPlugin.instance.getLogger().info("[WorldManager] World " + worldName + "_playable was loaded!");
    }

    public void unloadWorld(String worldName) {
        BedwarsPlugin.instance.getLogger().info("[WorldManager] Unloading world " + worldName + "_playable...");
        BedwarsPlugin.instance.getServer().unloadWorld(worldName + "_playable", false);
        if(deleteDirectory(worldName + "_playable")) {
            BedwarsPlugin.instance.getLogger().info("[WorldManager] World " + worldName + "_playable was unloaded!");
        } else {
            BedwarsPlugin.instance.getLogger().severe("[WorldManager] Error unloading world" + worldName + "_playable!");
        }
    }

    @SuppressWarnings("resource")
    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private boolean deleteDirectory(String path) {
        File directoryToBeDeleted = new File(path);
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if(!deleteDirectory(file)) return false;
            }
        }
        return directoryToBeDeleted.delete();
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if(!deleteDirectory(file)) return false;
            }
        }
        return directoryToBeDeleted.delete();
    }

}
