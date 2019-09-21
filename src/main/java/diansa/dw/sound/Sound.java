package diansa.dw.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sound {

    private static ExecutorService SOUND_SERVICE = Executors.newCachedThreadPool();
    private final String soundFile;
    private final Clip clip;

    public Sound(String soundFile) {
        try {
            AudioInputStream baseStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(soundFile));
            AudioFormat baseFormat = baseStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodeStream = AudioSystem.getAudioInputStream(decodeFormat, baseStream);
            this.clip = AudioSystem.getClip();
            this.clip.open(decodeStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        this.soundFile = soundFile;
    }

    public void play() {
        SOUND_SERVICE.submit(() -> {
            if (!clip.isRunning()) {
                clip.stop();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
        });
    }

    public void loop() {
        SOUND_SERVICE.submit(() -> {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        });
    }

    public void stop() {
        clip.stop();
    }

    public void setGain(double value) {
    }
}
