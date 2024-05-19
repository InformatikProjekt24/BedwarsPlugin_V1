package de.kidinthedark.bedwarsplugin.commands;


import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        LanguagePlaceholder placeholder = new LanguagePlaceholder();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("bedwars.start")) {
                if(BedwarsPlugin.instance.gameManager.startLobbyCountdown()) {
                    MessageFactory.sendMessage("game_start_success", placeholder, player);
                } else {
                    MessageFactory.sendMessage("game_start_fail", placeholder, player);
                }
            } else {
                MessageFactory.sendMessage("command_no_permission", placeholder, player);
            }
        } else {
            if(BedwarsPlugin.instance.gameManager.startLobbyCountdown()) {
                BedwarsPlugin.instance.getLogger().info(MessageFactory.getMessage("game_start_success", placeholder, ConfigVars.defaultLanguage));
            } else {
                BedwarsPlugin.instance.getLogger().info(MessageFactory.getMessage("game_start_fail", placeholder, ConfigVars.defaultLanguage));
            }
        }

        return true;
    }
}
