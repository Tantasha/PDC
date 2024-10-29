package MillionaireGame.database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private String[] answers;
    private int correctAnswerIndex;
    private int value;

    // Constructor
    public Question(String questionText, String[] answers, int correctAnswerIndex, int value) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.value = value;
    }

    // Getter for answers
    public String[] getAnswers() {
        return answers;
    }

    // Method to check if an answer is correct
    public boolean isCorrectAnswer(int index) {
        return index == correctAnswerIndex;
    }

    // Getter for question text
    public String getQuestionText() {
        return questionText;
    }

    // Getter for question value
    public int getValue() {
        return value;
    }

    // Method to load questions from a file
    public static Question[] loadQuestion(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assume format: question;answer1;answer2;answer3;answer4;correctAnswerIndex;value
                String[] parts = line.split(";");
                String questionText = parts[0];
                String[] answers = {parts[1], parts[2], parts[3], parts[4]};
                int correctAnswerIndex = Integer.parseInt(parts[5]);
                int value = Integer.parseInt(parts[6]);
                questions.add(new Question(questionText, answers, correctAnswerIndex, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions.toArray(new Question[0]);
    }
}
