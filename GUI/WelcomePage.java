package MillionaireGame.GUI;

import MillionaireGame.Project1.GameRules;
import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Playtech
 */
public class WelcomePage extends JPanel {

    private final MainMenu mainMenu;
    private Image backgroundImage;

    public WelcomePage(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setLayout(new BorderLayout());
        setBackground(Color.white);

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/image/bg.png/"));
        } catch (IOException ex) {
            System.err.println("Background failed to load.");
        }

        // Set up button panel with FlowLayout
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 150));
        btnPanel.setOpaque(false);

        // Create buttons
        JButton startGameBtn = new JButton("Start Game");
        JButton gameRuleBtn = new JButton("Game Rules");

        //size of the button
        startGameBtn.setPreferredSize(new Dimension(150, 50));
        gameRuleBtn.setPreferredSize(new Dimension(150, 50));

        //button font
        Font btnFont = new Font("Verdana", Font.BOLD, 14);
        startGameBtn.setFont(btnFont);
        gameRuleBtn.setFont(btnFont);

        //button colour
        startGameBtn.setBackground(new Color(0x4B4B4B)); 
        startGameBtn.setForeground(Color.white); 
        gameRuleBtn.setBackground(new Color(0x4B4B4B)); 
        gameRuleBtn.setForeground(Color.white); // Black text

        //mouse hover effect
        startGameBtn.addMouseListener(new java.awt.event.MouseAdapter() {
           @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startGameBtn.setBackground(new Color(0x2F2F2F)); 
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startGameBtn.setBackground(new Color(0x4B4B4B)); 
            }
        });
        
        gameRuleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                gameRuleBtn.setBackground(new Color(0x2F2F2F)); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                gameRuleBtn.setBackground(new Color(0x4B4B4B)); 
            }
        });

        // Add action listeners
        startGameBtn.addActionListener(ex -> promptName());
        gameRuleBtn.addActionListener(ex -> showGameRules());

        // Add buttons to the panel
        btnPanel.add(startGameBtn);
        btnPanel.add(gameRuleBtn);

        // Add button panel to the center of the Welcome Page
        add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    //Once the player clicks start game button, prompt the player to enter name.
    private void promptName() {
        String playerName = JOptionPane.showInputDialog(this, "Please Enter Your Name: ", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName != null && !playerName.trim().isEmpty()) {
            System.out.println("Player's Name: " + playerName);
            mainMenu.getPlayer().setName(playerName);
            mainMenu.startGame();
        } else {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid Name.");
        }
    }

    //display rules
    private void showGameRules() {
        GameRules gameRules = new  GameRules();
        String rules =  gameRules.displayRules();
        JOptionPane.showMessageDialog(this, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
