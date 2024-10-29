package MillionaireGame.database;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class GamePanel extends JPanel {
    private GameState gameState;
    private Question[] questions;
    private int currentQuestionIndex;
    private JLabel moneyLabel;
    private Question currentQuestion;
    private JButton[] answerButtons;
    
    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        this.currentQuestionIndex = 0;
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        loadQuestions();
    }
    
    private void initializeComponents() {
        // Question display area
        JPanel questionPanel = new JPanel(new BorderLayout(5, 5));
        moneyLabel = new JLabel("$0", SwingConstants.CENTER);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionPanel.add(moneyLabel, BorderLayout.NORTH);
        
        // Answer buttons
        JPanel answersPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            final int index = i;
            answerButtons[i] = new JButton();
            answerButtons[i].addActionListener(e -> handleAnswer(index));
            answersPanel.add(answerButtons[i]);
        }
        
        add(questionPanel, BorderLayout.CENTER);
        add(answersPanel, BorderLayout.SOUTH);
    }
    
    private void loadQuestions() {
        questions = Question.loadQuestion("src/resources/question.txt");
        if (questions.length > 0) {
            updateQuestion(questions[0]);
        }
    }
    
    public void updateQuestion(Question question) {
        this.currentQuestion = question;
        // Update UI with question details
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(question.getAnswers()[i]);
        }
    }
    
    private void handleAnswer(int index) {
        if (currentQuestion.isCorrectAnswer(index)) {
            gameState.correctAnswer();
            moneyLabel.setText("$" + gameState.getMoney());
            
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                JOptionPane.showMessageDialog(this, "Correct! Moving to next question.");
                updateQuestion(questions[currentQuestionIndex]);
            } else {
                JOptionPane.showMessageDialog(this, "Congratulations! You've won the game!");
                gameState.gameOver();
                DatabaseOperations.saveScore(gameState.getPlayerName(), gameState.getMoney());
            }
        } else {
            gameState.gameOver();
            JOptionPane.showMessageDialog(this, "Wrong Answer! Game Over!");
            DatabaseOperations.saveScore(gameState.getPlayerName(), gameState.getMoney());
        }
    }
}