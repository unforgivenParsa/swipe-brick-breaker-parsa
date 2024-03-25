package brickBreaker;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundSystem {
    private Clip clip;
    boolean pause = false;
    public SoundSystem() {
        try {
            File audioFile = new File("Wolf Hoffmann - Adagio in G Minor.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.CLOSE && !pause) {
                    clip.setFramePosition(0);
                    clip.start();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            pause = false;
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            pause = true;
            clip.stop();
        }
    }

}