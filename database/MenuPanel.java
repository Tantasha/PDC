package MillionaireGame.database;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton highScoresButton;
    private JButton exitButton;
    
    public MenuPanel() {
        setLayout(new GridBagLayout());
        initializeComponents();
    }
    
    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Who Wants to Be a Millionaire?", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, gbc);
        
        startButton = new JButton("New Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        add(startButton, gbc);
        
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.PLAIN, 18));
        add(highScoresButton, gbc);
        
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        add(exitButton, gbc);
    }
    
    public JButton getStartButton() {
        return startButton;
    }
    
    public JButton getHighScoresButton() {
        return highScoresButton;
    }
    
    public JButton getExitButton() {
        return exitButton;
    }
}