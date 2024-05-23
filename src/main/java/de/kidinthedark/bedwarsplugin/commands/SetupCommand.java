package de.kidinthedark.bedwarsplugin.commands;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.FileBuilder;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        LanguagePlaceholder placeholder = new LanguagePlaceholder();

        if (sender instanceof Player player) {
            if (player.hasPermission("bedwars.setup")) {
                if (args.length > 0) {
                    FileBuilder builder = new FileBuilder(BedwarsPlugin.instance.getDataFolder() + "/savedMaps", player.getWorld().getName() + ".yml");
                    builder.mkfile();
                    builder.copyDefaults(true);

                    if (args[0].equalsIgnoreCase("mapname")) {
                        builder.addDefault("mapName", args[1]);
                    } else if (args[0].equalsIgnoreCase("teamsize")) {
                        builder.addDefault("teamSize", args[1]);
                    }
                } else {
                    //todo: "You did not provide any arguments when running the command. Try again or type '/help setup'."
                }
            } else {
                //todo: "You don't have permission to use this command."
            }

        } else {
            BedwarsPlugin.instance.getLogger().info("Only players can execute this command.");
        }



        return true;
    }

}
