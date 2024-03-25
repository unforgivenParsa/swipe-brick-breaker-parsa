package brickBreaker;

import java.awt.*;

public class Earthquake extends Innermost {
    public Earthquake(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.decode("#88471E");
    }

    @Override
    public Color getDefaultColor() {
        return Color.decode("#88471E");
    }

    @Override
    public void toDo() {
        gamePanel.timeEarth = 10000;
    }
}
