package brickBreaker;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

enum Level {
        easy, semiPro, legendary;
}

public class Application implements Runnable {
        SoundSystem soundManager;
        JPanel mainPanel;
        GamePanel gamePanel;
        Timer timer;
        JFrame frame;
        String history;
        boolean isAim, isSong, isSave, isMovable;
        int refreshRare, bestScore;
        Level difficulty;
        Color ballColor;
        String name;
        JButton blueButton, orangeButton, blackButton, easy, semiPro, legendary;
        public Application () throws UnsupportedAudioFileException, LineUnavailableException, IOException {
                //musicPlayer = new MusicPlayer();
                frame = new JFrame();
                frame.setSize(450,800);
                frame.setVisible(true);
                mainPanel = (JPanel)frame.getContentPane();
                refreshRare = 120;
                bestScore = 0;
                isSong = true;
                Performance();
        }

        public void Performance() throws FileNotFoundException {
                history = "";
                Scanner sc = new Scanner(new File("Performance.txt"));
                while (sc.hasNextLine()) {
                        String string = sc.nextLine();
                        history += string + '\n';
                        int index = 0, score = 0;
                        while (index < string.length() && string.charAt(index) != ' ') {
                                score = (score*10) + (string.charAt(index)-'0');
                                index++;
                        }
                        bestScore = Math.max(bestScore, score);
                }
        }
        public void HomePage() {
                MyPanel homePanel = new MyPanel();
                homePanel.setLayout(null);
                mainPanel.add(homePanel);
                JButton newGame = new JButton("NEW GAME");
                newGame.setBounds(100,250,250,50);
                newGame.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                NewGamePanelSpecifications();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                homePanel.add(newGame);
                JButton historyButton = new JButton("HISTORY");
                historyButton.setBounds(100,350,250,50);
                historyButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                try {
                                        initialHistoryPanel();
                                } catch (FileNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                }
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                homePanel.add(historyButton);
                JButton setting = new JButton("SETTING");
                setting.setBounds(100,450,250,50);
                setting.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                SettingPage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                homePanel.add(setting);
                JButton exit = new JButton("EXIT");
                exit.setBounds(100,550,250,50);
                exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                frame.dispose();
                        }
                });
                homePanel.add(exit);
                JLabel bestScoreLable = new JLabel("<html><h1>RECORD : " + String.valueOf(bestScore) + "<h1><html>", SwingConstants.CENTER);
                bestScoreLable.setBounds(100, 150, 250, 50);
                homePanel.add(bestScoreLable);
        }
        public void SettingPage() {
                MyPanel settingPanel = new MyPanel();
                settingPanel.setLayout(null);
                mainPanel.add(settingPanel);
                //
                JButton back = new JButton("Back");
                back.setBounds(150,600,150,50);
                settingPanel.add(back);
                back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                HomePage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                JLabel labelAim = new JLabel();
                labelAim.setText(" Target : ");
                labelAim.setBounds(100, 200, 100, 50);
                JLabel labelSong = new JLabel();
                labelSong.setText(" Song : ");
                labelSong.setBounds(100, 300, 100, 50);
                JLabel labelSave = new JLabel();
                labelSave.setText("Save : ");
                labelSave.setBounds(100, 400, 100, 50);
                settingPanel.add(labelAim);
                settingPanel.add(labelSong);
                settingPanel.add(labelSave);
                //
                JButton aim = new JButton(isAim ? " ON " : " OFF ");
                aim.setBounds(250,200,100,50);
                aim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isAim = !isAim;
                                mainPanel.removeAll();
                                SettingPage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(aim);
                JButton song = new JButton(isSong ? "ON" : "OFF");
                song.setBounds(250,300,100,50);
                song.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isSong = !isSong;
                                if (isSong) {
                                        soundManager = new SoundSystem();
                                } else {
                                        soundManager.stop();
                                }
                                if (isSong) {

                                } else {

                                }
                                mainPanel.removeAll();
                                SettingPage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(song);
                JButton save = new JButton(isSave ? "ON" : "OFF");
                save.setBounds(250,400,100,50);
                save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isSave = !isSave;
                                mainPanel.removeAll();
                                SettingPage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(save);
        }
        public void initialHistoryPanel () throws FileNotFoundException {
                MyPanel historyPanel = new MyPanel();
                historyPanel.setLayout(null);
                mainPanel.add(historyPanel);
                JButton back = new JButton("Back");
                back.setBounds(150,600,150,50);
                historyPanel.add(back);
                back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                HomePage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                JTextArea display = new JTextArea(10,10);
                JScrollPane scrollPane = new JScrollPane(display);
                scrollPane.setBounds(50,50,340,500);
                historyPanel.add(scrollPane);
                display.setFont(new Font("Comic Sans", Font.BOLD,20));
                display.setText(history);

        }

        public void endgameSpecification(int score) {
                MyPanel gameOverPanel = new MyPanel();
                gameOverPanel.setLayout(null);
                mainPanel.add(gameOverPanel);
                JButton back = new JButton("Back");
                back.setBounds(100,550,100,50);
                gameOverPanel.add(back);
                back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                HomePage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                JButton start = new JButton("Start");
                start.setBounds(240,550,100,50);
                gameOverPanel.add(start);
                start.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (name != null && difficulty != null && ballColor != null) {
                                        mainPanel.removeAll();
                                        gameBeginning();
                                        mainPanel.revalidate();
                                        mainPanel.repaint();
                                }
                        }
                });
                //
                JLabel scoreLable = new JLabel("<html><h1>SCORE : " + score + "<h1><html>", SwingConstants.CENTER);
                scoreLable.setBounds(100,250,240,50);
                gameOverPanel.add(scoreLable);

        }
        public void NewGamePanelSpecifications() {
                MyPanel newGamePanel = new MyPanel();
                newGamePanel.setLayout(null);
                mainPanel.add(newGamePanel);
                JButton back = new JButton("Back");
                back.setBounds(100,550,100,50);
                newGamePanel.add(back);
                back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                HomePage();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                JButton start = new JButton("Start");
                start.setBounds(240,550,100,50);
                newGamePanel.add(start);
                start.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (name != null && difficulty != null && ballColor != null) {
                                        mainPanel.removeAll();
                                        gameBeginning();
                                        mainPanel.revalidate();
                                        mainPanel.repaint();
                                }
                        }
                });
                //
                difficulty = null;
                JLabel levelLabel = new JLabel("Level : ");
                levelLabel.setBounds(25,200,90,50);
                newGamePanel.add(levelLabel);
                easy = new JButton("Easy");
                easy.setBounds(125,200,90,50);
                newGamePanel.add(easy);
                easy.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                setDifficulty(Level.easy);
                                resetGameLevel();
                                easy.setBackground(Color.decode("#22ABF0"));
                                easy.setForeground(Color.WHITE);
                        }
                });
                semiPro = new JButton("Semi-Pro");
                semiPro.setBounds(225,200,90,50);
                newGamePanel.add(semiPro);
                semiPro.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                setDifficulty(Level.semiPro);
                                resetGameLevel();
                                semiPro.setBackground(Color.decode("#22ABF0"));
                                semiPro.setForeground(Color.WHITE);
                        }
                });
                legendary = new JButton("Legendary");
                legendary.setBounds(325,200,90,50);
                newGamePanel.add(legendary);
                legendary.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                setDifficulty(Level.legendary);
                                resetGameLevel();
                                legendary.setBackground(Color.decode("#22ABF0"));
                                legendary.setForeground(Color.WHITE);
                        }
                });
                //
                ballColor = null;
                JLabel colorLabel = new JLabel("My Ball Color : ");
                colorLabel.setBounds(25,300,90,50);
                newGamePanel.add(colorLabel);
                blackButton = new JButton("Black");
                blackButton.setBounds(125,300,90,50);
                newGamePanel.add(blackButton);
                blackButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                resetColorButton();
                                setBallColor(Color.BLACK);
                                blackButton.setBackground(Color.black);
                                blackButton.setForeground(Color.WHITE);
                        }
                });
                orangeButton = new JButton("Orange");
                orangeButton.setBounds(225,300,90,50);
                newGamePanel.add(orangeButton);
                orangeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                resetColorButton();
                                setBallColor(Color.ORANGE);
                                orangeButton.setBackground(Color.ORANGE);
                        }
                });
                blueButton = new JButton("Blue");
                blueButton.setBounds(325,300,90,50);
                newGamePanel.add(blueButton);
                blueButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                resetColorButton();
                                setBallColor(Color.BLUE);
                                blueButton.setBackground(Color.BLUE);
                                blueButton.setForeground(Color.WHITE);
                        }
                });
                //
                name = null;
                JLabel nameLabel = new JLabel("My Name : ");
                nameLabel.setBounds(25,400,90,50);
                newGamePanel.add(nameLabel);
                JTextField textField = new JTextField(" Name");
                textField.setBounds(125,400,190,50);
                textField.setFont(new Font("Comic Sans", Font.BOLD,15));
                newGamePanel.add(textField);
                JButton confirm = new JButton("Confirm");
                confirm.setBounds(325,400,90,50);
                newGamePanel.add(confirm);
                confirm.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                name = textField.getText();
                                confirm.setBackground(Color.decode("#22ABF0"));
                                confirm.setForeground(Color.WHITE);
                        }
                });
                //
        }
        @Override
        public void run() {
                HomePage();
                soundManager = new SoundSystem();
                soundManager.play();
        }

        public void gameBeginning() {
                gamePanel = null;
                if (difficulty.equals(Level.easy)) {
                        gamePanel = new GamePanel(this, 700, ballColor, 17);
                }
                if (difficulty.equals(Level.semiPro)) {
                        gamePanel = new GamePanel(this, 550, ballColor, 20);
                }
                if (difficulty.equals(Level.legendary)) {
                        gamePanel = new GamePanel(this, 400, ballColor, 25);

                }
                gamePanel.setLayout(null);
                mainPanel.add(gamePanel);
                isMovable = true;
                mainPanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                if (e.getY() < gamePanel.defaultSpawnY && isMovable && mainPanel.contains(e.getX(), e.getY())) {
                                        gamePanel.setxClicked(e.getX());
                                        gamePanel.setyClicked(e.getY());
                                        if (gamePanel.dizzy) {
                                                gamePanel.setxClicked((int) (Math.random() * 450));
                                                gamePanel.setyClicked((int) (Math.random() * gamePanel.defaultSpawnY));
                                        }
                                        setMovable(false);
                                        for (Ball ball : gamePanel.balls) {
                                                ball.isMovable = true;
                                                ball.setVx(10 * (gamePanel.xClicked - gamePanel.currentSpawnX) / (int) distance(gamePanel.currentSpawnX, gamePanel.currentSpawnY, gamePanel.xClicked, gamePanel.yClicked));
                                                ball.setVy(10 * (gamePanel.yClicked - gamePanel.currentSpawnY) / (int) distance(gamePanel.currentSpawnX, gamePanel.currentSpawnY, gamePanel.xClicked, gamePanel.yClicked));
                                        }
                                        gamePanel.lastSpawnX = gamePanel.currentSpawnX;
                                        gamePanel.lastSpawnY = gamePanel.currentSpawnY;
                                        gamePanel.currentSpawnX = 1000;
                                        gamePanel.currentSpawnY = 1000;
                                }
                        }
                });
                timer = new Timer(1000 / refreshRare, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                gamePanel.time += 20;
                                gamePanel.power = 1;
                                gamePanel.timePower -= 20;
                                if (gamePanel.timePower > 0) {
                                        gamePanel.power = 2;
                                }
                                gamePanel.velocityRatio = 1;
                                gamePanel.timeSpeed -= 20;
                                if (gamePanel.timeSpeed > 0) {
                                        gamePanel.velocityRatio = 2;
                                }
                                gamePanel.randomColor = false;
                                gamePanel.inRandomColor = false;
                                if (gamePanel.timeColor > 0) {
                                        gamePanel.inRandomColor = true;
                                        if (gamePanel.timeColor % 200 == 0) {
                                                gamePanel.randomColor = true;
                                        }
                                }
                                gamePanel.timeColor -= 20;
                                if (isMovable) {
                                        try {
                                                gamePanel.nextFrame();
                                        } catch (Exception ex) {
                                                throw new RuntimeException(ex);
                                        }
                                } else {
                                        for (int i = 0; i < gamePanel.velocityRatio; i++) {
                                                try {
                                                        gamePanel.nextGame();
                                                } catch (Exception ex) {
                                                        throw new RuntimeException(ex);
                                                }
                                        }
                                }
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                timer.start();
        }
        public void endGame (int score) throws Exception {
                timer.stop();
                mainPanel.removeAll();
                endgameSpecification(score);
                mainPanel.revalidate();
                mainPanel.repaint();
                if (isSave) {
                        File file = new File("Performance.txt");
                        bestScore = Math.max(bestScore, score);
                        Calendar now = Calendar.getInstance();
                        history =  name + " score : "+ score + " at "+  now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + "\n" +history;
                        PrintWriter printWriter = new PrintWriter(file);
                        printWriter.print(history);
                        printWriter.flush();
                        printWriter.close();
                }
        }

        public JPanel getMainPanel() {
                return mainPanel;
        }

        public void setMainPanel(JPanel mainPanel) {
                this.mainPanel = mainPanel;
        }

        public Timer getTimer() {
                return timer;
        }

        public void setTimer(Timer timer) {
                this.timer = timer;
        }

        public JFrame getFrame() {
                return frame;
        }

        public void setFrame(JFrame frame) {
                this.frame = frame;
        }

        public boolean isAim() {
                return isAim;
        }

        public void setAim(boolean aim) {
                isAim = aim;
        }

        public boolean isSong() {
                return isSong;
        }

        public void setSong(boolean song) {
                isSong = song;
        }

        public boolean isSave() {
                return isSave;
        }

        public void setSave(boolean save) {
                isSave = save;
        }

        public int getRefreshRare() {
                return refreshRare;
        }

        public void setRefreshRare(int refreshRare) {
                this.refreshRare = refreshRare;
        }

        public boolean isMovable() {
                return isMovable;
        }

        public Level getDifficulty() {
                return difficulty;
        }

        public void setDifficulty(Level difficulty) {
                this.difficulty = difficulty;
        }

        public Color getBallColor() {
                return ballColor;
        }

        public void setBallColor(Color ballColor) {
                this.ballColor = ballColor;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setMovable(boolean movable) {
                isMovable = movable;
        }
        public double distance (int x1, int y1, int x2, int y2) {
                return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        }
        public void resetColorButton () {
                orangeButton.setBackground(null);
                blackButton.setBackground(null);
                blackButton.setForeground(Color.BLACK);
                blueButton.setBackground(null);
                blueButton.setForeground(Color.BLACK);
        }
        public void resetGameLevel() {
                easy.setBackground(null);
                easy.setForeground(Color.BLACK);
                semiPro.setBackground(null);
                semiPro.setForeground(Color.BLACK);
                legendary.setBackground(null);
                legendary.setForeground(Color.BLACK);
        }
}
