package de.kidinthedark.bedwarsplugin.commands.tabcompleter;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
                if(args.length >= 4) {
                    if(player.getTargetBlockExact(5) == null) {
                        return null;
                    }

                    Location block = Objects.requireNonNull(player.getTargetBlockExact(5)).getLocation();

                    return switch (args.length) {
                        case 4, 7 -> List.of(block.getBlockX() + "");
                        case 5, 8 -> List.of(block.getBlockY() + "");
                        case 6, 9 -> List.of(block.getBlockZ() + "");
                        default -> null;
                    };
                }
            }

        } else if (args.length == 4 && args[0].equalsIgnoreCase("generator") && args[2].equalsIgnoreCase("type")) {
            return Arrays.asList("iron", "gold", "diamond", "emerald");
        }

        return List.of();
    }
}
