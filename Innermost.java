package brickBreaker;

import java.awt.*;

public abstract class Innermost {
    GamePanel gamePanel;
    Color color;
    int currentX, currentY;
    public Innermost(GamePanel gamePanel, int currentX, int currentY) {
        this.gamePanel = gamePanel;
        this.currentX = currentX;
        this.currentY = currentY;
    }
    public abstract void toDo ();
    public abstract Color getDefaultColor ();

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
