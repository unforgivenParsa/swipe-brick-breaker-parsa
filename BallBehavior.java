package brickBreaker;

import java.awt.*;

public class BallBehavior extends Innermost {


    public BallBehavior(GamePanel gamePanel, int currentX, int currentY) {
        super(gamePanel, currentX, currentY);
        color = Color.GREEN;
    }
    public Color getDefaultColor () {
        return Color.green;
    }

    @Override
    public void toDo() {
        gamePanel.addBallCounter++;
    }
}
