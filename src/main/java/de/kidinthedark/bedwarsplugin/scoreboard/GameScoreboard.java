package de.kidinthedark.bedwarsplugin.scoreboard;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import de.kidinthedark.bedwarsplugin.util.ConfigVars;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class GameScoreboard {

    public Scoreboard scoreboard;
    public ScoreboardData scoreboardData;

    public GameScoreboard() {
        scoreboard = BedwarsPlugin.instance.getServer().getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewObjective("gameobjective", Component.text(ConfigVars.scoreboardTitle))
    }

    public void updateData(ScoreboardData scoreboardData) {

        if(scoreboardData == null) {
            this.scoreboardData = scoreboardData;
            return;
        }

        if(scoreboardData.getScoreboardLines() != this.scoreboardData.getScoreboardLines()) {
            throw new IllegalArgumentException("Scoreboard can't change it's number of lines!");
        }
        this.scoreboardData = scoreboardData;
    }

    public void sendScoreboard() {
        switch (BedwarsPlugin.instance.gameManager.getGameState()) {
            case INGAME:
            case LOBBY:
            case PREGAME:
            case POSTGAME:
            case ENDLOBBY:
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.setScoreboard(scoreboard);
            }
        }
    }


}
