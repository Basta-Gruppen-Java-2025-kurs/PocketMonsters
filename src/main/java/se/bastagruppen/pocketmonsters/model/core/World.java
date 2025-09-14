package se.bastagruppen.pocketmonsters.model.core;

import se.bastagruppen.pocketmonsters.model.entities.Forest;
import se.bastagruppen.pocketmonsters.model.entities.Location;
import se.bastagruppen.pocketmonsters.model.entities.Route;
import se.bastagruppen.pocketmonsters.model.entities.Town;

import java.util.Map;

import static se.bastagruppen.pocketmonsters.model.core.Direction.*;

public final class World {

    public static final Location START;

    static {
        Location palletTown = new Town("Pallet Town", "pallet_town.wav");
        Location viridianCity = new Town("Viridian City", "viridian_city.wav");
        Location route1 = new Route("Route 1", "route_1.wav");
        Location route3 = new Route("Route 3", "route_3.wav");
        Location forest1 = new Forest("Lost Woods I", "forest.wav");
        Location forest2 = new Forest("Lost Woods II", "forest.wav");
        Location forest3 = new Forest("Lost Woods III", "forest.wav");
        Location forest4 = new Forest("Lost Woods IV", "forest.wav");

        palletTown.addPaths(Map.of(NORTH, route1));
        route1.addPaths(Map.of(SOUTH, palletTown, NORTH, viridianCity));
        viridianCity.addPaths(Map.of(SOUTH, route1, WEST, route3, NORTH, forest1));
        route3.addPaths(Map.of(EAST, viridianCity));
        forest1.addPaths(Map.of(SOUTH, viridianCity, NORTH, forest2, WEST, forest4));
        forest2.addPaths(Map.of(SOUTH, forest1, WEST, forest3));
        forest3.addPaths(Map.of(EAST, forest2, SOUTH, forest4));
        forest4.addPaths(Map.of(NORTH, forest3, EAST, forest1));

        START = palletTown;
    }

    private World() {}
}
