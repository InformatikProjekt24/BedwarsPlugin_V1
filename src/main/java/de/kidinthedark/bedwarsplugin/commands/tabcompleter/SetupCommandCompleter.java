package de.kidinthedark.bedwarsplugin.commands.tabcompleter;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetupCommandCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("mapname", "teamsize", "team", "generator");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("team")) {
            return Arrays.asList("colour", "spawn", "bed", "teamshop", "upgradeshop");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("generator")) {
            return Arrays.asList("type", "spawn");
        } else if (args.length == 4 && args[0].equalsIgnoreCase("team") && args[2].equalsIgnoreCase("colour")) {
            return Arrays.asList("red", "blue", "yellow", "green", "aqua", "white", "pink", "gray");
        } else if (args[0].equalsIgnoreCase("team") && args[2].equalsIgnoreCase("bed")) {
            if (sender instanceof Player player) {
                if (args.length == 4) {
                    return Collections.singletonList(Double.toString(player.getX()));
                } else if (args.length == 5) {
                    return Collections.singletonList(Double.toString(player.getY()));
                } else if (args.length == 6) {
                    return Collections.singletonList(Double.toString(player.getZ()));
                } else {
                    //todo: checken ob das hier funktioniert
                    //sollte eig die Koordinaten 1 Block vor dem Spieler zurückgeben

                    Location eyes = player.getEyeLocation();
                    Vector direction = eyes.getDirection();

                    if (args.length == 7) {
                        return Collections.singletonList(Double.toString(direction.multiply(2).getX()));
                    }
                    if (args.length == 8) {
                        return Collections.singletonList(Double.toString(direction.multiply(2).getY()));
                    }
                    if (args.length == 9) {
                        return Collections.singletonList(Double.toString(direction.multiply(2).getZ()));
                    }
                }
            }

        } else if (args.length == 4 && args[0].equalsIgnoreCase("generator") && args[2].equalsIgnoreCase("type")) {
            return Arrays.asList("iron", "gold", "diamond", "emerald");
        }

        return List.of();
    }
}
