package brickBreaker;

import java.awt.*;

public class ItemPower extends Item{

    public ItemPower(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.decode("#6750A4");
    }

    @Override
    public void toDo() {
        gamePanel.timePower = 15000;
    }
}

