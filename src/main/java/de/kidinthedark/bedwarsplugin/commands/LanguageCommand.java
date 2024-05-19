package de.kidinthedark.bedwarsplugin.commands;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LanguageCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        LanguagePlaceholder placeholder = new LanguagePlaceholder();

        if(sender instanceof Player player) {

            if(args.length != 1) {
                MessageFactory.sendMessage("command_language_usage", placeholder, player);
                return true;
            }

            placeholder.updatePlaceholder("language", args[0]);

            if(!BedwarsPlugin.instance.languageLoader.getLocales().contains(args[0])) {
                MessageFactory.sendMessage("command_language_missing", placeholder, player);
                return true;
            }

            BedwarsPlugin.instance.languageLoader.setPlayerLanguage(player, args[0]);
            MessageFactory.sendMessage("command_language_success", placeholder, player);

        } else {
            BedwarsPlugin.instance.getLogger().info("Only players can execute this command.");
        }

        return true;
    }

}
