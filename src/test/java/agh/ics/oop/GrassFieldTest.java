package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    IWorldMap map = new GrassField(10);

    @Test
    public void canMoveOnEmptyMapTest() {
        assertTrue(map.canMoveTo(new Vector2d(25,19)));
        assertTrue(map.canMoveTo(new Vector2d(2,8)));
    }

    @Test
    public void placeTest() {
        assertTrue(map.place(new Animal(map, new Vector2d(2,6))));
        assertFalse(map.place(new Animal(map, new Vector2d(2,6))));
        assertTrue(map.place(new Animal(map, new Vector2d(12,25))));
    }

    @Test
    public void canMoveOnNotEmptyMapTest() {
        map.place(new Animal(map, new Vector2d(7,3)));
        assertFalse(map.canMoveTo(new Vector2d(7,3)));
        assertTrue(map.canMoveTo(new Vector2d(2,5)));
    }

    @Test
    public void isOccupiedTest() {
        map.place(new Animal(map, new Vector2d(20,5)));
        assertTrue(map.isOccupied(new Vector2d(20,5)));
        assertFalse(map.isOccupied(new Vector2d(10,10)));
    }

    @Test
    public void objectAtTest() {
        Vector2d position = new Vector2d(3,3);
        Animal animal = new Animal(map, position);
        Animal animal2 = new Animal(map, new Vector2d(1,2));
        map.place(animal);
        assertEquals(animal, map.objectAt(position));
        assertNotEquals(animal2, map.objectAt(new Vector2d(1,2)));
    }
}
