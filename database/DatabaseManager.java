package MillionaireGame.database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:derby:millionaireDB;create=true";
    private static Connection connection;
    
    public static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
            createTableIfNotExists();
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
    
    private static void createTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            // Check if table exists
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, "SCORES", null);
            
            if (!res.next()) {
                // Create scores table if it doesn't exist
                String createTableSQL = 
                    "CREATE TABLE scores (" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "player_name VARCHAR(100) NOT NULL, " +
                    "score INTEGER NOT NULL, " +
                    "date_played TIMESTAMP NOT NULL, " +
                    "PRIMARY KEY (id))";
                
                stmt.executeUpdate(createTableSQL);
                System.out.println("Created scores table.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating database tables: " + e.getMessage());
        }
    }
    
   public static void disconnect() {
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        System.out.println("Database disconnected successfully.");
    } catch (SQLException e) {
        System.err.println("Error closing database connection: " + e.getMessage());
    }
}

    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection status: " + e.getMessage());
        }
        return connection;
    }
}