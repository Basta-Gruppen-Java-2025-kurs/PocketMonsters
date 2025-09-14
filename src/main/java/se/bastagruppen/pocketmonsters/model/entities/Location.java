package se.bastagruppen.pocketmonsters.model.entities;

import se.bastagruppen.pocketmonsters.model.core.Direction;
import se.bastagruppen.pocketmonsters.model.audio.SoundEngine;

import java.util.*;

public abstract class Location {

    protected final String name;
    protected final SoundEngine backgroundMusic;
    protected final Map<Direction, Location> paths;

    protected static final int MAX_PATHS = 4;
    protected static final String DIRECTORY_PATH = "audio/soundtracks/";

    public Location(String name, String fileName) {
        this.name = name;
        this.backgroundMusic = new SoundEngine(DIRECTORY_PATH + fileName);
        this.paths = new HashMap<>(MAX_PATHS);
    }

    public String getName() {
        return name;
    }

    public SoundEngine getBackgroundMusic() {
        return backgroundMusic;
    }

    public abstract void interact();

    public Map<Direction, Location> getPaths() {
        return new HashMap<>(paths);
    }

    public void addPaths(Map<Direction, Location> paths) {
        if (paths.size() > MAX_PATHS)
            throw new IllegalArgumentException("Only " + MAX_PATHS + " paths allowed");

        this.paths.putAll(paths);
    }

    public Location move(Direction direction) {
        boolean directionPresent = paths.containsKey(direction);
        if (!directionPresent)
            throw new RuntimeException("No such direction: " + direction);

        return paths.get(direction);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location that = (Location) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}