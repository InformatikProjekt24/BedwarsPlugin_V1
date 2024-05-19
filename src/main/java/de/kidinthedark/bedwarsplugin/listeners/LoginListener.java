package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        LanguagePlaceholder placeholder = new LanguagePlaceholder();
        if(!BedwarsPlugin.instance.gameManager.allowJoin()) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Component.text(MessageFactory.getMessage("login_join_disallow", placeholder, e.getPlayer())));
        } else if (Bukkit.getOnlinePlayers().size() == ConfigVars.maxPlayers) {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, Component.text(MessageFactory.getMessage("login_join_full", placeholder, e.getPlayer())));
        }
    }

}
