package de.kidinthedark.bedwarsplugin.listeners;

import de.kidinthedark.bedwarsplugin.util.LanguagePlaceholder;
import de.kidinthedark.bedwarsplugin.util.MessageFactory;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        LanguagePlaceholder placeholder = new LanguagePlaceholder();
        MessageFactory.sendMessage("lobby_info_join", placeholder, event.getPlayer());
    }

}
