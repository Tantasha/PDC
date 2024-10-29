package MillionaireGame.database;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MillionaireGame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameState gameState;
    private Question[] questions;

    public MillionaireGame() {
        setTitle("Who Wants to Be a Millionaire?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Load questions at startup
        loadQuestions();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initializeComponents();
        setupListeners();

        add(mainPanel);
    }

    private void initializeComponents() {
        // Initialize menu panel
        menuPanel = new MenuPanel();
        mainPanel.add(menuPanel, "MENU");

        // Show menu panel by default
        cardLayout.show(mainPanel, "MENU");
    }

    private void setupListeners() {
        // Setup menu button listeners
        menuPanel.getStartButton().addActionListener(e -> startNewGame());
        menuPanel.getHighScoresButton().addActionListener(e -> showHighScores());
        menuPanel.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void loadQuestions() {
        try {
            questions = Question.loadQuestion("resources/question.txt");
            if (questions == null || questions.length == 0) {
                throw new Exception("No questions loaded");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading questions: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void startNewGame() {
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            gameState = new GameState(playerName);
            gamePanel = new GamePanel(gameState);
            mainPanel.add(gamePanel, "GAME");
            cardLayout.show(mainPanel, "GAME");

            // Start the game with the first question
            if (questions != null && questions.length > 0) {
                gamePanel.updateQuestion(questions[0]);
            }
        }
    }

    private void showHighScores() {
        // Create a formatted string of high scores
        List<Score> highScores = DatabaseOperations.getHighScores();
        StringBuilder sb = new StringBuilder();
        sb.append("High Scores:\n\n");

        for (int i = 0; i < highScores.size(); i++) {
            Score score = highScores.get(i);
            sb.append(String.format("%d. %s - $%,d (%s)\n",
                i + 1,
                score.getPlayerName(),
                score.getScore(),
                score.getDatePlayed().toString()
            ));
        }

        JOptionPane.showMessageDialog(this,
            sb.toString(),
            "High Scores",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            // Initialize database connection
            DatabaseManager.connect();

            SwingUtilities.invokeLater(() -> {
                new MillionaireGame().setVisible(true);
            });

            // Add shutdown hook to close database connection
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (DatabaseManager.getConnection() != null) {
                    DatabaseManager.disconnect();
                }
            }));

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
        System.exit(0);
    }
}
