package se.bastagruppen.pocketmonsters.model.core;

import se.bastagruppen.pocketmonsters.model.entities.Location;
import se.bastagruppen.pocketmonsters.model.graphics.Graphics;

import java.util.Random;
import java.util.Scanner;

import static se.bastagruppen.pocketmonsters.model.core.GameEffects.*;

public final class GameEngine {

    private Location currentLocation;
    private static final Scanner SCANNER = new Scanner(System.in);

    public GameEngine() {
        this.currentLocation = World.START;
    }

    public void startGame() {
        String action;
        Direction direction;

        while (true) {
            try {
                Graphics graphics = new Graphics(currentLocation);
                currentLocation.getBackgroundMusic().play();

                graphics.renderMenu();

                System.out.print("> ");
                action = SCANNER.nextLine().toLowerCase().trim();
                if ("Quit".equalsIgnoreCase(action))
                    break;

                currentLocation.getBackgroundMusic().stop();

                direction = Direction.parse(action);
                currentLocation = currentLocation.move(direction);
            } catch (Exception e) {
                wrongPath();
                continue;
            }
            walkingAnimation(direction.getLabel());
        }
    }

    public void walkingAnimation(String direction) {
        effectWithSound("audio/soundeffects/guide.wav", () -> {
            Random random = new Random();
            System.out.printf("Walking %s", direction);
            for (int i = 0; i < random.nextInt(2, 7); i++) {
                System.out.print(".");
                tick(500);
            }
            System.out.println();
        });
    }

    public void wrongPath() {
        effectWithSound("audio/soundeffects/bump.wav", () -> {
            System.out.println("Wrong Path!");
            tick(250);
        });
    }
}