package MillionaireGame.database;

import java.sql.Timestamp;

public class Score {
    private String playerName;
    private int score;
    private Timestamp datePlayed;
    
    public Score(String playerName, int score, Timestamp datePlayed) {
        this.playerName = playerName;
        this.score = score;
        this.datePlayed = datePlayed;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    public Timestamp getDatePlayed() {
        return datePlayed;
    }
}