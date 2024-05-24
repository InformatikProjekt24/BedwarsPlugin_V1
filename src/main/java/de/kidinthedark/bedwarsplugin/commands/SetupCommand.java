package de.kidinthedark.bedwarsplugin.commands;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.FileBuilder;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
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
            if (player.hasPermission("bedwars.start")) {
                if (args.length > 0) {
                    FileBuilder builder = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath() + "/savedMaps", player.getWorld().getName() + ".yml");
                    builder.mkfile();
                    builder.copyDefaults(true);

                    if (args[0].equalsIgnoreCase("mapname")) {
                        builder.addDefault("mapName", args[1]);
                        //todo: "Map name was set to " + args[1]
                    } else if (args[0].equalsIgnoreCase("teamsize")) {
                        builder.addDefault("teamSize", args[1]);
                        //todo: "Team size was set to " + args[1]
                    } else if (args[0].equalsIgnoreCase("team")) {
                        try {
                            int teamNumber;
                            teamNumber = Integer.parseInt(args[1]);
                            String key = "teams.t_" + teamNumber;

                            if (args[2].equalsIgnoreCase("colour")) {
                                builder.addDefault(key + ".colour", args[3].toUpperCase());
                            } else if (args[2].equalsIgnoreCase("spawn")) {
                                builder.addDefault(key + ".spawn.x", player.getX());
                                builder.addDefault(key + ".spawn.y", player.getY());
                                builder.addDefault(key + ".spawn.z", player.getZ());
                                builder.addDefault(key + ".spawn.yaw", player.getYaw());
                                builder.addDefault(key + ".spawn.pit", player.getPitch());
                            } else if (args[2].equalsIgnoreCase("bed") && args.length == 9) {
                                builder.addDefault(key + ".bed.x1", Integer.parseInt(args[3]));
                                builder.addDefault(key + ".bed.y1", Integer.parseInt(args[4]));
                                builder.addDefault(key + ".bed.z1", Integer.parseInt(args[5]));
                                builder.addDefault(key + ".bed.x2", Integer.parseInt(args[6]));
                                builder.addDefault(key + ".bed.y2", Integer.parseInt(args[7]));
                                builder.addDefault(key + ".bed.z2", Integer.parseInt(args[8]));
                            } else if(args[2].equalsIgnoreCase("teamshop")) {
                                builder.addDefault(key + ".team_shop.x", player.getX());
                                builder.addDefault(key + ".team_shop.y", player.getY());
                                builder.addDefault(key + ".team_shop.z", player.getZ());
                            } else if(args[2].equalsIgnoreCase("upgradeshop")) {
                                builder.addDefault(key + ".upgrade_shop.x", player.getX());
                                builder.addDefault(key + ".upgrade_shop.y", player.getY());
                                builder.addDefault(key + ".upgrade_shop.z", player.getZ());
                            } else {
                                //todo: "Error: Wrong use of command."
                            }

                        } catch (NumberFormatException e) {
                            //todo: "You did not declare a number where a number should be declared."
                        }

                    } else if (args[0].equalsIgnoreCase("generator")) {
                        try {
                            int genNumber;
                            genNumber = Integer.parseInt(args[1]);
                            String key = "generators.g_" + genNumber;

                            if (args[2].equalsIgnoreCase("type")) {
                                builder.addDefault(key + ".type", args[3].toUpperCase());
                            } else if (args[2].equalsIgnoreCase("spawn")) {
                                builder.addDefault(key + ".x", player.getX());
                                builder.addDefault(key + ".y", player.getY());
                                builder.addDefault(key + ".z", player.getZ());
                            } else {
                                //todo: "Error: Wrong use of command."
                            }

                        } catch (NumberFormatException e) {
                            //todo: "You did not declare a number where a number should be declared."
                        }
                    }

                    builder.save();
                } else {
                    //todo: "You did not provide any arguments when running the command. Try again or type '/help setup'."
                }
            } else {
                MessageFactory.sendMessage("command_no_permission", placeholder, player);
            }

        } else {
            BedwarsPlugin.instance.getLogger().info("Only players can execute this command.");
        }

        return true;
    }

}
