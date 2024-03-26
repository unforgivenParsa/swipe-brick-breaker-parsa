package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    int possibilty, height, xClicked, yClicked, defaultSpawnY, currentSpawnX, currentSpawnY, lastSpawnX, lastSpawnY;
    int time, timePower, timeSpeed, timeColor, velocityRatio, timeEarth, addBallCounter, power;
    float score;
    Application application;
    Color color;
    Boolean dizzy = false, addDizzy = false, randomColor = false, inRandomColor = false;
    ArrayList<Ball> balls;
    ArrayList<Scores> rects;
    ArrayList<Innermost> items;

    public GamePanel(Application application, int defaultSpawnY, Color color, int possibility) {
        setBackground(Color.WHITE);
        power = 1;
        score = 0;
        time = 0;
        this.defaultSpawnY = defaultSpawnY;
        this.application = application;
        this.color = color;
        this.possibilty = possibility;
        currentSpawnX = 225;
        currentSpawnY = defaultSpawnY;
        balls = new ArrayList<>();
        balls.add(new Ball(currentSpawnX, currentSpawnY));
        rects = new ArrayList<>();
        items = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawLine(0, defaultSpawnY, 450, defaultSpawnY);
        for (Ball ball : balls) {
            Ellipse2D ballShape = new Ellipse2D.Double(ball.currentX - 10, ball.currentY - 10, 20, 20);
            g2d.setColor(Color.decode("#383838"));
            g2d.fill(ballShape);
            ballShape = new Ellipse2D.Double(ball.currentX - 7, ball.currentY - 7, 14, 14);
            if (!inRandomColor) {
                ball.setColor(application.ballColor);
            } else if (randomColor) {
                ball.setColor(randomColorGenerator());
            }
            g2d.setColor(ball.getColor());
            g2d.fill(ballShape);
        }
        for (Innermost item : items) {
            Ellipse2D ballShape = new Ellipse2D.Double(item.currentX - 10, item.currentY - 10, 20, 20);
            if (!inRandomColor) {
                item.setColor(item.getDefaultColor());
            } else if (randomColor) {
                item.setColor(randomColorGenerator());
            }
            g2d.setColor(item.getColor());
            g2d.fill(ballShape);
        }
        for (Scores rect : rects) {
            if (!inRandomColor) {
                rect.setColor(Color.BLACK);
            } else if (randomColor) {
                rect.setColor(randomColorGenerator());
            }
            g2d.setColor(rect.getColor());
            g2d.fillRect(rect.x, rect.y, 50, 50);
            g2d.setColor(Color.WHITE);
            g2d.drawString(String.valueOf(rect.currentScore), (rect.x) + 25, rect.y + 25);
        }
        if (application.isMovable && application.isAim) {
            int mouseLocationX = (int) MouseInfo.getPointerInfo().getLocation().getX();
            int mouseLocationY = (int) MouseInfo.getPointerInfo().getLocation().getY();
            if (dizzy) {
                mouseLocationX = (int) (Math.random() * 450);
                mouseLocationY = (int) (Math.random() * defaultSpawnY);
            }
            drawLine(mouseLocationX, mouseLocationY, currentSpawnX, currentSpawnY, g);
        }
    }

    public void drawLine(int mouseLocationX, int mouseLocationY, int currentSpawnX, int currentSpawnY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1, new float[]{10}, 0);
        g2d.setStroke(stroke);
        int Vx = 10 * (mouseLocationX - currentSpawnX) / (int) distance(currentSpawnX, currentSpawnY, mouseLocationX, mouseLocationY);
        int Vy = 10 * (mouseLocationY - currentSpawnY) / (int) distance(currentSpawnX, currentSpawnY, mouseLocationX, mouseLocationY);
        int x1 = currentSpawnX, y1 = currentSpawnY;
        while (true) {
            x1 += Vx;
            y1 += Vy;
            if (x1 >= 440) {
                g2d.drawLine(currentSpawnX, currentSpawnY, 440, y1);
                return;
            }
            if (x1 <= 10) {
                g2d.drawLine(currentSpawnX, currentSpawnY, 10, y1);
                return;
            }
            if (y1 <= 10) {
                g2d.drawLine(currentSpawnX, currentSpawnY, x1, 10);
                return;
            }
            if (y1 >= defaultSpawnY) {
                g2d.drawLine(currentSpawnX, currentSpawnY, x1, defaultSpawnY);
                return;
            }
            for (Scores rect : rects) {
                if (Vy > 0 && y1 + 10 >= (rect.y) && y1 + 10 < (rect.y + 50)) {
                    if (x1 >= rect.x && x1 <= rect.x + 50) {
                        g2d.drawLine(currentSpawnX, currentSpawnY, x1, rect.y - 10);
                        return;
                    }
                }
                if (Vy < 0 && y1 - 10 <= (rect.y + 50) && y1 - 10 > (rect.y)) {
                    if (x1 >= rect.x && x1 <= rect.x + 50) {
                        g2d.drawLine(currentSpawnX, currentSpawnY, x1, rect.y + 50 + 10);
                        return;
                    }
                }
                if (Vx > 0 && x1 + 10 >= (rect.x) && x1 + 10 < (rect.x + 50)) {
                    if (y1 >= rect.y && y1 <= rect.y + 50) {
                        g2d.drawLine(currentSpawnX, currentSpawnY, rect.x - 10, y1);
                        return;
                    }
                }
                if (Vx < 0 && x1 - 10 <= (rect.x + 50) && x1 - 10 > (rect.x)) {
                    if (y1 >= rect.y && y1 <= rect.y + 50) {
                        g2d.drawLine(currentSpawnX, currentSpawnY, rect.x + 10, y1);
                        return;
                    }
                }
            }
        }
    }

    public Color randomColorGenerator() {
        int random = (int) (Math.random() * 10);
        if (random == 0) {
            return Color.WHITE;
        } else if (random == 1) {
            return Color.BLACK;
        } else if (random == 2) {
            return Color.BLUE;
        } else if (random == 3) {
            return Color.ORANGE;
        } else if (random == 4) {
            return Color.RED;
        } else if (random == 5) {
            return Color.decode("#1D8D85");
        } else if (random == 6) {
            return Color.GREEN;
        } else if (random == 7) {
            return Color.decode("#1D8D85");
        } else if (random == 8) {
            return Color.MAGENTA;
        } else if (random == 9) {
            return Color.decode("#88471E");
        }
        return null;
    }

    public void nextFrame() throws Exception {
        for (Scores rect : rects) {
            rect.y++;
            if (rect.y >= defaultSpawnY - 50) {
                application.endGame((int) score);
                return;
            }
        }
        for (int i = 0; i < items.size(); i++) {
            Innermost item = items.get(i);
            item.currentY++;
            if (item.currentY >= defaultSpawnY - 10) {
                items.remove(i);
                i--;
            }
        }
        if (height % 50 == 0) {
            randomComponentGenerator();
        }
        height++;
    }

    public void randomComponentGenerator() {
        for (int i = 0; i < 9; i++) {
            int random = (int) (Math.random() * 100);
            if (random < possibilty) {
                if (application.difficulty.equals(Level.easy)) {
                    rects.add(new Scores(i * 50, -50, height / 50 + 1));
                } else if (application.difficulty.equals(Level.semiPro)) {
                    rects.add(new Scores(i * 50, -50, height / 50 * 2 + 1));
                } else {
                    rects.add(new Scores(i * 50, -50, height / 50 * 3 + 1));
                }
            }
            if (random >= 90) {
                random = (int) (Math.random() * 12);
                if (random < 4) {
                    items.add(new BallBehavior(this, i * 50 + 25, -25));
                } else if (random < 6) {
                    items.add(new Speed(this, i * 50 + 25, -25));
                } else if (random < 8) {
                    items.add(new PowerBehavior(this, i * 50 + 25, -25));
                } else if (random < 10) {
                    items.add(new Confusion(this, i * 50 + 25, -25));
                } else if (random == 10) {
                    items.add(new Colorful(this, i * 50 + 25, -25));
                } else {
                    items.add(new Earthquake(this, i * 50 + 25, -25));
                }
            }

        }
    }

    public void nextGame() throws Exception {
        boolean isThereMove = false;
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball ball = balls.get(i);
            isThereMove = isThereMove || ball.isMovable;
            if (ball.isMovable) {
                ball.lastX = ball.currentX;
                ball.lastY = ball.currentY;
                ball.currentX += ball.getVx();
                ball.currentY += ball.getVy();
                // Collision with walls
                if (ball.currentX >= 440) {
                    ball.currentX = 880 - ball.currentX;
                    ball.Vx = (-1) * ball.Vx;
                }
                if (ball.currentX <= 10) {
                    ball.currentX = 20 - ball.currentX;
                    ball.Vx = (-1) * ball.Vx;
                }
                if (ball.currentY >= defaultSpawnY) {
                    ball.isMovable = false;
                    ball.currentY = defaultSpawnY;
                    if (currentSpawnX == 1000 && currentSpawnY == 1000) {
                        currentSpawnX = ball.currentX;
                        currentSpawnY = defaultSpawnY;
                    }
                    ball.currentX = currentSpawnX;
                }
                if (ball.currentY <= 10) {

                    ball.lastY = 10;
                    //
                    ball.currentY = 20 - ball.currentY;
                    ball.Vy = (-1) * ball.Vy;
                }
                if (ball.lastX == lastSpawnX && ball.lastY == lastSpawnY) {
                    break;
                }
                for (int j = 0; j < rects.size(); j++) {
                    Scores rect = rects.get(j);
                    boolean collision = false;
                    if (ball.Vy > 0 && ball.lastY + 10 < (rect.y) && ball.currentY + 10 >= (rect.y)) {
                        // important
                        double tempX = (double) (ball.Vx / ball.Vy) * (rect.y - (ball.lastY + 10)) + ball.lastX;
                        if (tempX >= rect.x && tempX <= rect.x + 50) {
                            ball.Vy = (-1) * ball.Vy;
                            ball.currentY = 2 * (rect.y - 10) - ball.currentY;
                            rect.currentScore -= power;
                            collision = true;
                        }
                    }
                    if (!collision && ball.Vy < 0 && ball.lastY - 10 > (rect.y + 50) && ball.currentY - 10 <= (rect.y + 50)) {
                        // important
                        double tempX = (double) (ball.Vx / ball.Vy) * ((rect.y + 50) - (ball.lastY - 10)) + ball.lastX;
                        if (tempX >= rect.x && tempX <= rect.x + 50) {
                            ball.Vy = (-1) * ball.Vy;
                            ball.currentY = 2 * (rect.y + 60) - ball.currentY;
                            rect.currentScore -= power;
                            collision = true;
                        }
                    }
                    if (!collision && ball.Vx > 0 && ball.lastX + 10 < (rect.x) && ball.currentX + 10 >= (rect.x)) {
                        double tempY = (double) (ball.Vy / ball.Vx) * (rect.x - (ball.lastX + 10)) + ball.lastY;
                        if (tempY >= rect.y && tempY <= rect.y + 50) {
                            ball.Vx = (-1) * ball.Vx;
                            ball.currentX = 2 * (rect.x - 10) - ball.currentX;
                            rect.currentScore -= power;
                            collision = true;
                        }
                    }
                    if (!collision && ball.Vx < 0 && ball.lastX - 10 > (rect.x + 50) && ball.currentX - 10 <= (rect.x + 50)) {
                        double tempY = (double) (ball.Vy / ball.Vx) * ((rect.x + 50) - (ball.lastX - 10)) + ball.lastY;
                        if (tempY >= rect.y && tempY <= rect.y + 50) {
                            ball.Vx = (-1) * ball.Vx;
                            ball.currentX = 2 * (rect.x + 60) - ball.currentX;
                            rect.currentScore -= power;
                            collision = true;
                        }
                    }
                    if (!collision && distance(rect.x, rect.y, ball.currentX, ball.currentY) < 10) {
                        int temp = ball.Vx;
                        ball.Vx = (-1) * ball.Vy;
                        ball.Vy = (-1) * temp;
                        ball.currentX = ball.currentX + (int) (ball.Vx * (10 - distance(rect.x, rect.y, ball.currentX, ball.currentY)) / 10);
                        ball.currentY = ball.currentY + (int) (ball.Vy * (10 - distance(rect.x, rect.y, ball.currentX, ball.currentY)) / 10);
                        rect.currentScore -= power;
                        collision = true;
                    }
                    if (!collision && distance(rect.x + 50, rect.y, ball.currentX, ball.currentY) < 10) {
                        int temp = ball.Vx;
                        ball.Vx = ball.Vy;
                        ball.Vy = temp;
                        ball.currentX = ball.currentX + (int) (ball.Vx * (10 - distance(rect.x + 50, rect.y, ball.currentX, ball.currentY)) / 10);
                        ball.currentY = ball.currentY + (int) (ball.Vy * (10 - distance(rect.x + 50, rect.y, ball.currentX, ball.currentY)) / 10);
                        rect.currentScore -= power;
                        collision = true;
                    }
                    if (!collision && distance(rect.x, rect.y + 50, ball.currentX, ball.currentY) < 10) {
                        int temp = ball.Vx;
                        ball.Vx = ball.Vy;
                        ball.Vy = temp;
                        ball.currentX = ball.currentX + (int) (ball.Vx * (10 - distance(rect.x, rect.y + 50, ball.currentX, ball.currentY)) / 10);
                        ball.currentY = ball.currentY + (int) (ball.Vy * (10 - distance(rect.x, rect.y + 50, ball.currentX, ball.currentY)) / 10);
                        rect.currentScore -= power;
                        collision = true;
                    }
                    if (!collision && distance(rect.x + 50, rect.y + 50, ball.currentX, ball.currentY) < 10) {
                        int temp = ball.Vx;
                        ball.Vx = (-1) * ball.Vy;
                        ball.Vy = (-1) * temp;
                        ball.currentX = ball.currentX + (int) (ball.Vx * (10 - distance(rect.x + 50, rect.y + 50, ball.currentX, ball.currentY)) / 10);
                        ball.currentY = ball.currentY + (int) (ball.Vy * (10 - distance(rect.x + 50, rect.y + 50, ball.currentX, ball.currentY)) / 10);
                        rect.currentScore -= power;
                    }
                    if (ball.Vy == 0) {
                        ball.Vy = 1;
                    }
                    if (rect.currentScore <= 0) {
                        rects.remove(j);
                        score += (float) Math.max(30000, 60000 - time) * rect.originalScore / 60000;
                        j--;
                    }
                }
                for (int j = 0; j < items.size(); j++) {
                    Innermost item = items.get(j);
                    if (distance(item.currentX, item.currentY, ball.currentX, ball.currentY) < 20) {
                        item.toDo();
                        items.remove(j);
                        j--;
                    }
                }
            }
        }
        if (!isThereMove) {
            balls.add(new Ball(currentSpawnX, currentSpawnY));
            for (int i = 0; i < addBallCounter; i++) {
                balls.add(new Ball(currentSpawnX, currentSpawnY));
            }
            for (int i = 0; i < 25; i++) {
                nextFrame();
            }
            dizzy = addDizzy;
            addDizzy = false;
            addBallCounter = 0;
            application.isMovable = true;
        }
    }

    public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public int getxClicked() {
        return xClicked;
    }

    public void setxClicked(int xClicked) {
        this.xClicked = xClicked;
    }

    public int getyClicked() {
        return yClicked;
    }

    public void setyClicked(int yClicked) {
        this.yClicked = yClicked;
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }
}
