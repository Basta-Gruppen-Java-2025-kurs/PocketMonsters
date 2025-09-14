package se.bastagruppen.pocketmonsters.model.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;

public final class SoundEngine {

    private Clip audioClip;
    private final URL audioURL;
    private final int startLoopPoint;
    private final int endLoopPoint;

    public SoundEngine(String path, int startLoopPoint, int endLoopPoint) {
        if (startLoopPoint < 0)
            throw new IllegalArgumentException("Starting loop point must be greater than or equal to 0");

        if (endLoopPoint >= 0 && endLoopPoint < startLoopPoint)
            throw new IllegalArgumentException("Ending loop point must be -1 (loop to end) or at least the starting loop point");

        if (path == null || path.isBlank())
            throw new IllegalArgumentException("File name cannot be blank");

        this.startLoopPoint = startLoopPoint;
        this.endLoopPoint = endLoopPoint;
        this.audioURL = this.getClass().getClassLoader().getResource(path);
    }

    public SoundEngine(String fileName) {
        this(fileName, 0, LOOP_CONTINUOUSLY);
    }

    private void openAudioClip() {
        try {
            AudioInputStream audioInputStream = getAudioInputStream(requireNonNull(audioURL));
            audioClip = getClip();
            audioClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("The specified audio file is not supported", e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Audio line for playing back is unavailable", e);
        } catch (IOException e) {
            throw new RuntimeException("Error playing the audio file", e);
        }
    }

    private void setLoopPoints() {
        if (startLoopPoint != 0 || endLoopPoint != -1) {
            audioClip.setLoopPoints(startLoopPoint, endLoopPoint);
        }
    }

    public void play() {
        openAudioClip();
        audioClip.loop(LOOP_CONTINUOUSLY);
        setLoopPoints();
        audioClip.setFramePosition(0);
        audioClip.start();
    }

    public void stop() {
        audioClip.close();
        audioClip.flush();
    }
}