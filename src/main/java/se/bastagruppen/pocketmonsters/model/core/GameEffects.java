package se.bastagruppen.pocketmonsters.model.core;

import se.bastagruppen.pocketmonsters.model.audio.SoundEngine;

public final class GameEffects {

    @FunctionalInterface
    public interface Action extends Runnable {
        default void beforeExecution() {}
        default void afterExecution() {}
    }

    public static void effectWithSound(String path, Action action) {
        action.beforeExecution();

        SoundEngine sound = new SoundEngine(path);
        sound.play();
        try {
            action.run();
        } finally {
            sound.stop();
        }

        action.afterExecution();
    }

    public static void tick(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Tick interrupted", e);
        }
    }
}