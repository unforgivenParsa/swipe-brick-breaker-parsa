package brickBreaker;

import java.awt.*;

public abstract class Item {
    GamePanel gamePanel;
    Color color;
    int currentX, currentY;
    public Item (GamePanel gamePanel, int currentX, int currentY) {
        this.gamePanel = gamePanel;
        this.currentX = currentX;
        this.currentY = currentY;
    }
    public abstract void toDo ();
}
