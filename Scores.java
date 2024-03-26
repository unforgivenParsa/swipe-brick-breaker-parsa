package brickBreaker;

import java.awt.*;

import java.awt.*;

public class Scores {
    int x, y, currentScore, originalScore;
    Color color;
    public Scores(int x, int y, int score) {
        this.x = x;
        this.y = y;
        originalScore = score;
        currentScore = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getOriginalScore() {
        return originalScore;
    }

    public void setOriginalScore(int originalScore) {
        this.originalScore = originalScore;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
