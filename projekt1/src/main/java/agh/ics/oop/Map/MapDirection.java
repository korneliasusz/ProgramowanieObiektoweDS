package agh.ics.oop.Map;

import agh.ics.oop.Elements.Vector2d;

import java.util.Random;

public enum MapDirection {
    NORTH, SOUTH, EAST, WEST, NORTH_WEST, SOUTH_WEST, NORTH_EAST, SOUTH_EAST;

    private static Random rand = new Random();
    @Override
    public String toString() {
        return switch(this) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
            case NORTH_EAST -> "NE";
            case NORTH_WEST -> "NW";
            case SOUTH_EAST -> "SW";
            case SOUTH_WEST -> "SE";
        };
    }

    public MapDirection next() {
        return switch(this) {
            case NORTH -> NORTH_EAST;
            case SOUTH -> SOUTH_WEST;
            case EAST -> SOUTH_EAST;
            case WEST -> NORTH_WEST;
            case NORTH_EAST -> EAST;
            case NORTH_WEST -> NORTH;
            case SOUTH_EAST -> SOUTH;
            case SOUTH_WEST -> WEST;
        };
    }

    public Vector2d toUnitVector() {
        return switch(this) {
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTH_EAST -> new Vector2d(1,1);
            case NORTH_WEST -> new Vector2d(-1,1);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
        };
    }

    public static MapDirection randomOrientation() {
        MapDirection[] orientations = values();
        return orientations[rand.nextInt(orientations.length)];
    }
}
