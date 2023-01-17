package agh.ics.oop;

import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Map.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    MapDirection mNorth = MapDirection.NORTH;
    MapDirection mNorthEast = MapDirection.NORTH_EAST;
    MapDirection mEast = MapDirection.EAST;
    MapDirection mSouthEast = MapDirection.SOUTH_EAST;
    MapDirection mSouth = MapDirection.SOUTH;
    MapDirection mSouthWest = MapDirection.SOUTH_WEST;
    MapDirection mWest = MapDirection.WEST;
    MapDirection mNorthWest = MapDirection.NORTH_WEST;

    @Test
    public void nextMethodTest() {
        assertEquals(MapDirection.NORTH_EAST, mNorth.next());
        assertEquals(MapDirection.EAST, mNorthEast.next());
        assertEquals(MapDirection.SOUTH_EAST, mEast.next());
        assertEquals(MapDirection.SOUTH, mSouthEast.next());
        assertEquals(MapDirection.SOUTH_WEST, mSouth.next());
        assertEquals(MapDirection.WEST, mSouthWest.next());
        assertEquals(MapDirection.NORTH_WEST, mWest.next());
        assertEquals(MapDirection.NORTH, mNorthWest.next());
    }

    @Test
    public void toUnitVectorTest() {
        assertEquals(new Vector2d(0,1), mNorth.toUnitVector());
        assertEquals(new Vector2d(0,-1), mSouth.toUnitVector());
        assertEquals(new Vector2d(1,0), mEast.toUnitVector());
        assertEquals(new Vector2d(-1,0), mWest.toUnitVector());
        assertEquals(new Vector2d(1,1), mNorthEast.toUnitVector());
        assertEquals(new Vector2d(1,-1), mSouthEast.toUnitVector());
        assertEquals(new Vector2d(-1,-1), mSouthWest.toUnitVector());
        assertEquals(new Vector2d(-1,1), mNorthWest.toUnitVector());
    }
}
