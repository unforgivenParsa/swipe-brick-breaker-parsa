package brickBreaker;

import java.awt.*;

public class Colorful extends Innermost {
    public Colorful(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.MAGENTA;
    }
    public Color getDefaultColor () {
        return Color.MAGENTA;
    }
    @Override
    public void toDo() {
        gamePanel.timeColor = 10000;
    }
}
