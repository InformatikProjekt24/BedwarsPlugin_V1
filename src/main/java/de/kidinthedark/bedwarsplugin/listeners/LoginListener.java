package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.game.GameState;
import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(!BedwarsPlugin.instance.gameManager.getGameState().equals(GameState.LOBBY)) {

            LanguagePlaceholder placeholder = new LanguagePlaceholder();
            e.kickMessage(Component.text(MessageFactory.getMessage("login_join_disallow", placeholder, e.getPlayer())));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

}
