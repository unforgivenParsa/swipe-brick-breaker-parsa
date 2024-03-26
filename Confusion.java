package brickBreaker;

import java.awt.*;

public class Confusion extends Innermost {

    public Confusion(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.decode("#1D8D85");
    }
    public Color getDefaultColor () {
        return Color.decode("#1D8D85");
    }
    @Override
    public void toDo() {
        gamePanel.addDizzy = true;
    }
}
