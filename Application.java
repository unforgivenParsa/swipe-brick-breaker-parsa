package brickBreaker;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

enum Level {
        easy, medium, hard;
}

public class Application implements Runnable {
        SoundManager soundManager;
        JPanel mainPanel;
        GamePanel gamePanel;
        Timer timer;
        JFrame frame;
        String history;
        boolean isAim, isSong, isSave, isMovable;
        int refreshRare, time;
        Level difficulty;
        Color ballColor;
        String name;

        public Application() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
                //musicPlayer = new MusicPlayer();
                frame = new JFrame();
                frame.setSize(450, 800);
                frame.setVisible(true);
                mainPanel = (JPanel) frame.getContentPane();
                refreshRare = 50;
                history = "";
                isSong = true;
        }
        public void initialHomePanel () {
                MyPanel homePanel = new MyPanel();
                homePanel.setLayout(null);
                mainPanel.add(homePanel);
                JButton newGame = new JButton("NEW GAME");
                newGame.setBounds(100,200,250,50);
                newGame.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                initialNewGamePanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                homePanel.add(newGame);
                JButton history = new JButton("HISTORY");
                history.setBounds(100,300,250,50);
                history.addActionListener(new ActionListener() {
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
                homePanel.add(history);
                JButton setting = new JButton("SETTING");
                setting.setBounds(100,400,250,50);
                setting.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                mainPanel.removeAll();
                                initialSettingPanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                homePanel.add(setting);
                JButton exit = new JButton("EXIT");
                exit.setBounds(100,500,250,50);
                exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                frame.dispose();
                        }
                });
                homePanel.add(exit);
        }
        public void initialSettingPanel () {
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
                                initialHomePanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                JLabel labelAim = new JLabel();
                labelAim.setText("AIM : ");
                labelAim.setBounds(100, 200, 100, 50);
                JLabel labelSong = new JLabel();
                labelSong.setText("SONG : ");
                labelSong.setBounds(100, 300, 100, 50);
                JLabel labelSave = new JLabel();
                labelSave.setText("SAVE : ");
                labelSave.setBounds(100, 400, 100, 50);
                settingPanel.add(labelAim);
                settingPanel.add(labelSong);
                settingPanel.add(labelSave);
                //
                JButton aim = new JButton(isAim ? "Disable" : "Enable");
                aim.setBounds(250,200,100,50);
                aim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isAim = !isAim;
                                mainPanel.removeAll();
                                initialSettingPanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(aim);
                JButton song = new JButton(isSong ? "Disable" : "Enable");
                song.setBounds(250,300,100,50);
                song.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isSong = !isSong;
                                if (isSong) {
                                        soundManager = new SoundManager();
                                } else {
                                        soundManager.stop();
                                }
                                if (isSong) {
                                        // musicPlayer.resume();
                                } else {
                                        // musicPlayer.stop();
                                }
                                mainPanel.removeAll();
                                initialSettingPanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(song);
                JButton save = new JButton(isSave ? "Disable" : "Enable");
                save.setBounds(250,400,100,50);
                save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                isSave = !isSave;
                                mainPanel.removeAll();
                                initialSettingPanel();
                                mainPanel.revalidate();
                                mainPanel.repaint();
                        }
                });
                settingPanel.add(save);
        }

}
