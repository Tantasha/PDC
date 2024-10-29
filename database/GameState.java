package MillionaireGame.database;

public class GameState {
    private int currentMoney;
    private int questionNumber;
    private boolean gameOver;
    private String playerName;
    
    public GameState(String playerName) {
        this.playerName = playerName;
        this.currentMoney = 0;
        this.questionNumber = 0;
        this.gameOver = false;
    }
    
    public void correctAnswer() {
        currentMoney += 1000;
        questionNumber++;
        if (questionNumber == 15) {
            currentMoney *= 2;
            gameOver = true;
        }
    }
    
    public void gameOver() {
        gameOver = true;
    }
    
    public int getMoney() {
        return currentMoney;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}