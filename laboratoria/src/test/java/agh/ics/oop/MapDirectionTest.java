package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {

    MapDirection mSouth = MapDirection.SOUTH;
    MapDirection mNorth = MapDirection.NORTH;
    MapDirection mWest = MapDirection.WEST;
    MapDirection mEast = MapDirection.EAST;

    @Test
    public void nextMethodTest() {
        assertEquals(MapDirection.WEST, mSouth.next());
        assertEquals(MapDirection.EAST, mNorth.next());
        assertEquals(MapDirection.NORTH, mWest.next());
        assertEquals(MapDirection.SOUTH, mEast.next());
    }

    @Test
    public void previousMethodTest() {
        assertEquals(MapDirection.EAST, mSouth.previous());
        assertEquals(MapDirection.WEST, mNorth.previous());
        assertEquals(MapDirection.SOUTH, mWest.previous());
        assertEquals(MapDirection.NORTH, mEast.previous());
    }

}
