package se.bastagruppen.pocketmonsters.model.graphics;

import se.bastagruppen.pocketmonsters.model.core.Direction;
import se.bastagruppen.pocketmonsters.model.entities.Forest;
import se.bastagruppen.pocketmonsters.model.entities.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public record Graphics(Location currentLocation) {

    private void header(List<String> lines) {
        String locationName = currentLocation.getName();
        String locationType = currentLocation.getClass().getSimpleName();
        lines.add(locationType + ": " + locationName);
    }

    private void footer(List<String> lines) {
        final String LINE_FORMAT = "%7s: %s";
        lines.add("Paths:");

        if (currentLocation instanceof Forest) {
            lines.addAll(List.of(
                    format(LINE_FORMAT, "North", "?"),
                    format(LINE_FORMAT, "East", "?"),
                    format(LINE_FORMAT, "South", "?"),
                    format(LINE_FORMAT, "West", "?")
            ));
        } else {
            Map<Direction, Location> paths = currentLocation.getPaths();
            paths.keySet()
                    .stream()
                    .sorted()
                    .forEach(dir -> lines.add(
                            LINE_FORMAT.formatted(dir.getLabel(), paths.get(dir))
                    ));
        }
    }

    public void renderMenu() {
        List<String> lines = new ArrayList<>();
        header(lines);
        footer(lines);

        int contentSize = (lines.stream())
                .mapToInt(String::length)
                .max()
                .orElse(0);

        String topBorder = "┌" + "─".repeat(contentSize + 2) + "┐";
        String sepBorder = "├" + "─".repeat(contentSize + 2) + "┤";
        String botBorder = "└" + "─".repeat(contentSize + 2) + "┘";

        System.out.println(topBorder);
        printLine(lines.getFirst(), contentSize);
        System.out.println(sepBorder);
        for (int i = 1; i < lines.size(); i++) {
            printLine(lines.get(i), contentSize);
        }
        System.out.println(botBorder);
    }

    private static void printLine(String text, int inner) {
        System.out.println("│ " + padRight(text, inner) + " │");
    }

    private static String padRight(String s, int n) {
        int pad = Math.max(0, n - s.length());
        return s + " ".repeat(pad);
    }
}