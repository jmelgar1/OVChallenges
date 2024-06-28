package org.ovclub.ovchallenges.object.events;

public class ChallengeDTO {
    protected String name;
    protected int wins;
    protected int highScore;

    public ChallengeDTO(String name, int wins, int highScore) {
        this.name = name;
        this.wins = wins;
        this.highScore = highScore;
    }

    public String getName() { return name; }
    public int getWins() { return wins; }
    public void addWin() { wins += 1; }
    public int getHighScore() { return highScore; }
    public void checkToReplaceHighScore(int value) {
        if(value > highScore) {
            highScore = value;
        }
    }
}
