package MillionaireGame.GUI;

import MillionaireGame.Project1.Player;
import MillionaireGame.Project1.QuestionFile;
import MillionaireGame.Project1.Question;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private final Player player;
    private final Question[] questions;
    private int currentQuestionIndex = 0;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameWindow gameWindow;
    

    public MainMenu() {
        player = new Player("Player");
        questions = QuestionFile.loadQuestion("./resources/question.txt");
        
        
        setUpGUI();
    }

    private void setUpGUI() {
        setTitle("Who Wants to Be a Millionaire?");
        setSize(700, 650);
        setLocationRelativeTo(null); // center to the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        WelcomePage welcomePage = new WelcomePage(this);
        mainPanel.add(welcomePage, "Welcome");

        gameWindow = new GameWindow(this);
        mainPanel.add(gameWindow, "Game");

        setVisible(true);
    }

    public void startGame() {
        cardLayout.show(mainPanel, "Game");
        gameWindow.nextQuestion();
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void incrementCurrentQuestionIndex() {
        currentQuestionIndex++; // Increment the question index
    }

    public void endGame() {
    // Ensure that you're calling the instance method
    int finalMoney = gameWindow.currentMoney();
    JOptionPane.showMessageDialog(this, "Game Over!\n You Won: $" + finalMoney);
    System.exit(0);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
