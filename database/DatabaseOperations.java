package MillionaireGame.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    public static void saveScore(String playerName, int score) {
        String query = "INSERT INTO scores (player_name, score, date_played) VALUES (?, ?, NOW())";
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }
    
    public static List<Score> getHighScores() {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT * FROM scores ORDER BY score DESC LIMIT 10";
        
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                scores.add(new Score(
                    rs.getString("player_name"),
                    rs.getInt("score"),
                    rs.getTimestamp("date_played")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving high scores: " + e.getMessage());
        }
        return scores;
    }
}