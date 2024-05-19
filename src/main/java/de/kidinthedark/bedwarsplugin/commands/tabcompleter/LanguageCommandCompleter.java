package de.kidinthedark.bedwarsplugin.commands.tabcompleter;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LanguageCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return BedwarsPlugin.instance.languageLoader.getLocales();
        }
        return List.of();
    }

}
