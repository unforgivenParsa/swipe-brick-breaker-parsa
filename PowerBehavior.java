package brickBreaker;

import java.awt.*;

public class PowerBehavior extends Innermost {

    public PowerBehavior(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.decode("#1D8D85");
    }

    @Override
    public Color getDefaultColor() {
        return Color.decode("#1D8D85");
    }

    @Override
    public void toDo() {
        gamePanel.timePower = 15000;
    }
}


