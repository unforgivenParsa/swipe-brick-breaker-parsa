package brickBreaker;

import java.awt.*;

public class ItemSpeed extends Item{

    public ItemSpeed(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.RED;
    }

    @Override
    public void toDo() {
        gamePanel.timeSpeed = 15000;
    }
}
