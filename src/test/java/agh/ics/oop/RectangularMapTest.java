package agh.ics.oop;

import org.junit.jupiter.api.Test;

import javax.lang.model.type.NullType;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    IWorldMap map = new RectangularMap(10,5);

    @Test
    public void canMoveOnEmptyMapTest() {
        assertTrue(map.canMoveTo(new Vector2d(8,4)));
        assertFalse(map.canMoveTo(new Vector2d(11,4)));
        assertFalse(map.canMoveTo(new Vector2d(20,4)));
        assertFalse(map.canMoveTo(new Vector2d(-1,-5)));
    }

    @Test
    public void placeTest() {
        assertTrue(map.place(new Animal(map, new Vector2d(7,3))));
        assertTrue(map.place(new Animal(map, new Vector2d(2,5))));
        assertFalse(map.place(new Animal(map, new Vector2d(7,3))));
        assertFalse(map.place(new Animal(map, new Vector2d(-5,4))));
    }

    @Test
    public void canMoveOnNotEmptyMapTest() {
        map.place(new Animal(map, new Vector2d(7,3)));
        assertFalse(map.canMoveTo(new Vector2d(7,3)));
        assertTrue(map.canMoveTo(new Vector2d(2,5)));
    }

    @Test
    public void isOccupiedTest() {
        map.place(new Animal(map, new Vector2d(1,1)));
        map.place(new Animal(map, new Vector2d(-1,-7)));
        assertTrue(map.isOccupied(new Vector2d(1,1)));
        assertFalse(map.isOccupied(new Vector2d(2,1)));
        assertFalse(map.isOccupied(new Vector2d(7,3)));
        assertFalse(map.isOccupied(new Vector2d(-1,-7)));
    }

    @Test
    public void objectAtTest() {
        Vector2d position = new Vector2d(3,3);
        Animal animal = new Animal(map, position);
        map.place(animal);
        assertEquals(animal, map.objectAt(position));
        assertEquals(null, map.objectAt(new Vector2d(1,2)));
    }
}
