package MillionaireGame.GUI;

import MillionaireGame.Project1.AskTheAudience;
import MillionaireGame.Project1.FiftyFifty;
import MillionaireGame.Project1.PhoneAFriend;
import MillionaireGame.Project1.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameWindow extends JPanel {

    private final MainMenu mainMenu;
    private final JLabel questionLabel;
    private final JButton[] choiceButtons;
    private JLabel timerLabel;
    private final JLabel moneyLabel;
    private Timer timer;
    private int timeLeft;
    private int currentMoney;
    private final AskTheAudience askTheAudience;
    private final FiftyFifty fiftyFifty;
    private final PhoneAFriend phoneAFriend;
    private final JButton phoneAFriendButton;
    private final JButton askAudienceButton;
    private final JButton fiftyFiftyButton;

    public GameWindow(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setLayout(new BorderLayout());

        //Money label
        currentMoney = 0; // Start with zero money
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 16));

        //Time Label
        timerLabel = new JLabel("Time Left: 30");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout());
        statusPanel.add(timerLabel);
        statusPanel.add(moneyLabel);

        //Question Label
        questionLabel = new JLabel("Question will appear here");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setPreferredSize(new Dimension(200, 100));

        //Answer choice buttons
        choiceButtons = new JButton[4];
        JPanel choicesPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i] = new JButton("Choice " + (i + 1));
            choicesPanel.add(choiceButtons[i]);
            final int index = i;
            choiceButtons[i].addActionListener(e -> handleChoiceSelection(index));
        }

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(statusPanel, BorderLayout.NORTH);
        northPanel.add(questionLabel, BorderLayout.CENTER);

        JPanel helpPanel = new JPanel(new GridLayout(3, 1));
        helpPanel.setLayout(new FlowLayout());
        helpPanel.setPreferredSize(new Dimension(200, 150));

        //help option button
        //FiftyFIfty Button
        this.fiftyFiftyButton = new JButton("FiftyFifty");
        fiftyFiftyButton.setPreferredSize(new Dimension(150, 100));
        fiftyFiftyButton.setBackground(new Color(0x4B4B4B));
        fiftyFiftyButton.setForeground(Color.white);
        fiftyFiftyButton.addActionListener(e -> useFiftyFifty());
        helpPanel.add(fiftyFiftyButton);

        //Ask the Audience Button
        this.askAudienceButton = new JButton("Ask Audience");
        askAudienceButton.setPreferredSize(new Dimension(150, 100));
        askAudienceButton.setBackground(new Color(0x4B4B4B));
        askAudienceButton.setForeground(Color.white);
        askAudienceButton.addActionListener(e -> askAudience());
        helpPanel.add(askAudienceButton);

        //Phone a Friend Button
        this.phoneAFriendButton = new JButton("Phone a Friend");
        phoneAFriendButton.setPreferredSize(new Dimension(150, 100));
        phoneAFriendButton.setBackground(new Color(0x4B4B4B));
        phoneAFriendButton.setForeground(Color.white);
        phoneAFriendButton.addActionListener(e -> phoneAFriend());
        helpPanel.add(phoneAFriendButton);

        add(northPanel, BorderLayout.NORTH);
        add(choicesPanel, BorderLayout.CENTER);
        add(helpPanel, BorderLayout.SOUTH);

        //timer 
        timeLeft = 30;
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);
            if (timeLeft <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(GameWindow.this, "Time's up! Your final score is $" + currentMoney);
                mainMenu.endGame();
            }
        });

        askTheAudience = new AskTheAudience();
        fiftyFifty = new FiftyFifty();
        phoneAFriend = new PhoneAFriend();
    }

    public void startGame() {
        currentMoney = 0;
        moneyLabel.setText("Money: $0");

        timeLeft = 30;
        timerLabel.setText("Time Left: " + timeLeft);
        timer.start();

        nextQuestion();
    }
    
    //controls the button when the player answers correctly or uncorrectly
    private void handleChoiceSelection(int index) {
        if (mainMenu.getCurrentQuestionIndex() < 0 || mainMenu.getCurrentQuestionIndex() >= mainMenu.getQuestions().length) {
            JOptionPane.showMessageDialog(this, "Invalid question index. Please restart the game.");
            return;
        }

        Question currentQuestion = mainMenu.getQuestions()[mainMenu.getCurrentQuestionIndex()];
        String correctAnswer = currentQuestion.getCorrectAnswer();

        if (choiceButtons[index].getText().equals(correctAnswer)) {
            currentMoney += 50000;
            moneyLabel.setText("Money: $" + currentMoney);

            mainMenu.incrementCurrentQuestionIndex();
            nextQuestion();
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(GameWindow.this, "Sorry, that's incorrect. \nYour final score is $" + currentMoney);
            mainMenu.endGame();
        }
    }

 // shows the next question
public void nextQuestion() {
    if (mainMenu.getCurrentQuestionIndex() < mainMenu.getQuestions().length) {
        Question currentQuestion = mainMenu.getQuestions()[mainMenu.getCurrentQuestionIndex()];
        questionLabel.setText(currentQuestion.getQuestionText());

        String[] choices = currentQuestion.getChoices();
        for (int i = 0; i < choiceButtons.length; i++) {
            if (i < choices.length) {
                choiceButtons[i].setText(choices[i]);
                choiceButtons[i].setEnabled(true);
            } else {
                choiceButtons[i].setEnabled(false);
            }
        }

        timeLeft = 30;
        timerLabel.setText("Time Left: " + timeLeft);
        timer.restart();

        //will ask the player whether they want to play the las question or not
        if (mainMenu.getCurrentQuestionIndex() == 14) {
            Object[] options = {"Leave", "Play Last Question"};
            int choice = JOptionPane.showOptionDialog(this,
                    "You have reached the 14th question. Do you want to take your money and leave or play the last question?",
                    "Take Money or Play Last Question",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) { // Leave
                timer.stop();
                JOptionPane.showMessageDialog(this, "Congratulations! You have won $" + currentMoney);
                mainMenu.endGame();
            } else { // Play Last Question
                // Increment index for the last question
                mainMenu.incrementCurrentQuestionIndex();
                // Call nextQuestion to handle the last question
                displayLastQuestion();
            }
        }
    } else {
        timer.stop();
        // This block will not be reached since the above condition handles up to the last question
    }
}

// New method to handle the last question
private void displayLastQuestion() {
    // Get the last question
    Question currentQuestion = mainMenu.getQuestions()[mainMenu.getCurrentQuestionIndex()]; // This should be the last question
    questionLabel.setText(currentQuestion.getQuestionText()); // Display the last question text

    // Set up the choices for the last question
    String[] choices = currentQuestion.getChoices();
    for (int i = 0; i < choiceButtons.length; i++) {
        final int index = i; // Need to make it final to use in the lambda expression
        if (i < choices.length) {
            choiceButtons[i].setText(choices[i]);
            choiceButtons[i].setEnabled(true);
            
            // Remove existing action listeners
            for (ActionListener al : choiceButtons[i].getActionListeners()) {
                choiceButtons[i].removeActionListener(al);
            }
            
            // Add new action listener
            choiceButtons[i].addActionListener(e -> {
                if (choices[index].equals(currentQuestion.getCorrectAnswer())) {
                    currentMoney = (currentMoney + 50000) * 2; // Double the money if the answer is correct
                    JOptionPane.showMessageDialog(this, "Congratulations! You have won $" + currentMoney);
                } else {
                    JOptionPane.showMessageDialog(this, "Sorry, that's incorrect. You take nothing home.");
                    currentMoney = 0; // Set money to zero if the answer is incorrect
                }
                mainMenu.endGame(); // End the game after the last question
            });
        } else {
            choiceButtons[i].setEnabled(false);
        }
    }

    // Reset the timer for the last question
    timeLeft = 30;
    timerLabel.setText("Time Left: " + timeLeft);
    timer.restart();
}

    public int currentMoney() {
        return currentMoney; // Changed to follow Java naming conventions
    }

    public void updateCurrentMoney(int amount) {
        currentMoney += amount; 
        moneyLabel.setText("          Money: $" + currentMoney); // Update the display
    }

    public void useFiftyFifty() {
        // Check if the FiftyFifty option has already been used
        if (fiftyFifty.isUsed()) {
            JOptionPane.showMessageDialog(this, "You have already used FiftyFifty.", "Help Option Used", JOptionPane.WARNING_MESSAGE);
            return; // Exit if already used
        }

        // Get the current question
        Question currentQuestion = mainMenu.getQuestions()[mainMenu.getCurrentQuestionIndex()];

        // Use the FiftyFifty class
        fiftyFifty.use(currentQuestion);

        // Get the remaining choices from FiftyFifty
        List<Integer> remainingChoices = fiftyFifty.getRemainingChoices(); // This should return a list of two indices

        // Check if remainingChoices is null or doesn't contain exactly 2 items
        if (remainingChoices == null || remainingChoices.size() != 2) {
            JOptionPane.showMessageDialog(this, "Error retrieving remaining choices. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit if there's an issue
        }

        // Disable all choice buttons first
        for (JButton choiceButton : choiceButtons) {
            choiceButton.setEnabled(false);
        }

        // Enable only the correct answer and one incorrect answer
        for (int index : remainingChoices) {
            choiceButtons[index].setEnabled(true);
        }

        // Create a message to display the remaining choices
        StringBuilder message = new StringBuilder("The remaining choices are:\n");
        String[] choices = currentQuestion.getChoices();
        for (int index : remainingChoices) {
            message.append(choices[index]).append("\n");
        }

        // Display the remaining choices in a dialog
        JOptionPane.showMessageDialog(this , message.toString(), "50/50 Lifeline", JOptionPane.INFORMATION_MESSAGE);

        // Disable the FiftyFifty button after use
        fiftyFiftyButton.setEnabled(false);
    }

    public void askAudience() {
        // Check if the AskTheAudience option has already been used
        if (askTheAudience.isUsed()) {
            JOptionPane.showMessageDialog(this, "You have already used Ask The Audience.", "Help Option Used", JOptionPane.WARNING_MESSAGE);
            return; // Exit if already used
        }

        // Get the current question index
        int currentQuestionIndex = mainMenu.getCurrentQuestionIndex();
        if (currentQuestionIndex < 0 || currentQuestionIndex >= mainMenu.getQuestions().length) {
            JOptionPane.showMessageDialog(this, "Invalid question index. \nPlease restart the game.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current question
        Question currentQuestion = mainMenu.getQuestions()[currentQuestionIndex];

        // Use the AskTheAudience class to get the audience response
        askTheAudience.use(currentQuestion);
        String audienceResponse = askTheAudience.getAudienceResponse(currentQuestion);

        // Display the audience's response in a pop-up dialog
        JOptionPane.showMessageDialog(this, audienceResponse, "Ask The Audience", JOptionPane.INFORMATION_MESSAGE);

        // Disable the button once used
        askAudienceButton.setEnabled(false);
    }

    public void phoneAFriend() {
        if (phoneAFriend.isUsed()) {
            JOptionPane.showMessageDialog(this, "You have already used Phone A Friend.", "Help Option Used", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the current question
        Question currentQuestion = mainMenu.getQuestions()[mainMenu.getCurrentQuestionIndex()];

        // Use the PhoneAFriend option
        phoneAFriend.use(currentQuestion);

        // Get the friend's answer
        String friendAnswer = phoneAFriend.getFriendAnswer();

        // Display the friend's answer in a pop-up dialog
        JOptionPane.showMessageDialog(this, "Your friend thinks the answer is: " + friendAnswer, "Phone a Friend", JOptionPane.INFORMATION_MESSAGE);

        phoneAFriendButton.setEnabled(false);
    }
}
