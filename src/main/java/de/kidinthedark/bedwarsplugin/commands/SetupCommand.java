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
            if (player.hasPermission("bedwars.setup")) {
                if (args.length > 0) {
                    FileBuilder builder = new FileBuilder(BedwarsPlugin.instance.getDataFolder().toPath() + "/savedMaps", player.getWorld().getName() + ".yml");
                    builder.mkfile();
                    builder.copyDefaults(true);

                    if (args[0].equalsIgnoreCase("mapname")) {
                        builder.addDefault("mapName", args[1]);
                        placeholder.updatePlaceholder("mapName", args[1]);
                        MessageFactory.sendMessage("command_setup_map_name_set", placeholder, player);
                    } else if (args[0].equalsIgnoreCase("teamsize")) {
                        builder.addDefault("teamSize", args[1]);
                        placeholder.updatePlaceholder("teamSize", args[1]);
                        MessageFactory.sendMessage("command_setup_team_size_set", placeholder, player);
                    } else if (args[0].equalsIgnoreCase("team")) {
                        try {
                            int teamNumber;
                            teamNumber = Integer.parseInt(args[1]);
                            String key = "teams.t_" + teamNumber;

                            if (args[2].equalsIgnoreCase("color")) {
                                builder.addDefault(key + ".color", args[3].toUpperCase());
                            } else if (args[2].equalsIgnoreCase("spawn")) {
                                builder.addDefault(key + ".spawn.x", player.getX());
                                builder.addDefault(key + ".spawn.y", player.getY());
                                builder.addDefault(key + ".spawn.z", player.getZ());
                                builder.addDefault(key + ".spawn.yaw", player.getYaw());
                                builder.addDefault(key + ".spawn.pit", player.getPitch());
                            } else if (args[2].equalsIgnoreCase("bed") && args.length == 9) {
                                try {
                                    builder.addDefault(key + ".bed.x1", Integer.parseInt(args[3]));
                                    builder.addDefault(key + ".bed.y1", Integer.parseInt(args[4]));
                                    builder.addDefault(key + ".bed.z1", Integer.parseInt(args[5]));
                                    builder.addDefault(key + ".bed.x2", Integer.parseInt(args[6]));
                                    builder.addDefault(key + ".bed.y2", Integer.parseInt(args[7]));
                                    builder.addDefault(key + ".bed.z2", Integer.parseInt(args[8]));
                                } catch (NumberFormatException e) {
                                    MessageFactory.sendMessage("command_setup_bed_nan", placeholder, player);
                                }
                            } else if(args[2].equalsIgnoreCase("teamshop")) {
                                builder.addDefault(key + ".team_shop.x", player.getX());
                                builder.addDefault(key + ".team_shop.y", player.getY());
                                builder.addDefault(key + ".team_shop.z", player.getZ());
                            } else if(args[2].equalsIgnoreCase("upgradeshop")) {
                                builder.addDefault(key + ".upgrade_shop.x", player.getX());
                                builder.addDefault(key + ".upgrade_shop.y", player.getY());
                                builder.addDefault(key + ".upgrade_shop.z", player.getZ());
                            } else {
                                MessageFactory.sendMessage("command_setup_usage", placeholder, player);
                            }

                        } catch (NumberFormatException e) {
                            MessageFactory.sendMessage("command_setup_team_nan", placeholder, player);
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
                                MessageFactory.sendMessage("command_setup_usage", placeholder, player);
                            }

                        } catch (NumberFormatException e) {
                            MessageFactory.sendMessage("command_setup_generator_nan", placeholder, player);
                        }
                    }

                    builder.save();
                } else {
                    MessageFactory.sendMessage("command_setup_usage", placeholder, player);
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
