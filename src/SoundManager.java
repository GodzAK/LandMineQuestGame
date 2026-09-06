import javax.sound.sampled.*;
import java.net.URL;

/**
 * Loads sounds from the classpath (works no matter whose machine runs the
 * game) and plays them off the Event Dispatch Thread so the UI never stutters.
 */
public final class SoundManager {
    private SoundManager() {}

    public static void play(String resourceName) {
        // Never block Swing's UI thread waiting on audio I/O.
        Thread t = new Thread(() -> {
            try {
                URL url = SoundManager.class.getResource("/" + resourceName);
                if (url == null) {
                    url = SoundManager.class.getResource(resourceName);
                }
                if (url == null) return; // asset missing: fail silently, don't crash the game
                try (AudioInputStream stream = AudioSystem.getAudioInputStream(url)) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(stream);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                // Sound is a nice-to-have; never let it take the game down.
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
