package brickBreaker;

import java.awt.*;

public class Speed extends Innermost {

    public Speed(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.RED;
    }

    @Override
    public Color getDefaultColor() {
        return Color.RED;
    }

    @Override
    public void toDo() {
        gamePanel.timeSpeed = 15000;
    }
}
