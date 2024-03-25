package brickBreaker;

import java.awt.*;

public class ItemDizzy extends Item{

    public ItemDizzy(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.decode("#1D8D85");
    }

    @Override
    public void toDo() {
        gamePanel.addDizzy = true;
    }
}

