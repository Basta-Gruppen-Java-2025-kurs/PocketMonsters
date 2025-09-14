package se.bastagruppen.pocketmonsters.model.core;

import java.util.Arrays;
import java.util.Comparator;

public enum Direction implements Comparator<Direction> {

    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Direction parse(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Direction cannot be blank");

        final String direction = input.trim().toUpperCase();

        return Arrays.stream(values())
                .filter(d -> {
                    String name = d.name();
                    return ((name.equals(direction)) || (name.substring(0, 1).equals(direction)));
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid direction"));
    }

    @Override
    public int compare(Direction o1, Direction o2) {
        return Integer.compare(rank(o1), rank(o2));
    }

    private static int rank(Direction d) {
        return switch (d) {
            case NORTH -> 0;
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
        };
    }
}
