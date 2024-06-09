package de.kidinthedark.bedwarsplugin.scoreboard;

import java.util.HashMap;

public class ScoreboardData {

    private String title;
    private final HashMap<Integer, String> scoreboardLines;
    private final int scoreboardSize;

    public ScoreboardData(int scoreboardSize, String title) {
        scoreboardLines = new HashMap<>();
        this.scoreboardSize = scoreboardSize;
        this.title = title;
    }

    public HashMap<Integer, String> getScoreboardLines() {
        return scoreboardLines;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScoreboardLine(int scoreboardLine, String value) {
        if(scoreboardLine > scoreboardSize) {
            throw new ArrayIndexOutOfBoundsException("Scoreboard line " + scoreboardLine + " is greater than " + scoreboardSize);
        }
        scoreboardLines.put(scoreboardLine, value);
    }

}
