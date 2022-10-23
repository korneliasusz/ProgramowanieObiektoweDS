package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(8, 4);
    Vector2d v3 = new Vector2d(120, 300);
    Vector2d v4 = new Vector2d(125, 128);
    Vector2d v5 = new Vector2d(8,4);
    Vector2d v6 = new Vector2d(-10, -5);

    @Test
    public void equalsMethodTest() {
        assertTrue(v2.equals(v5));
        assertFalse(v2.equals(v4));
        assertFalse(v5.equals(v3));
        assertFalse(v6.equals(v2));
    }

    @Test
    public void toStringMethodTest() {
        assertEquals("(125,128)", v4.toString());
        assertEquals("(8,4)", v2.toString());
        assertEquals("(-10,-5)", v6.toString());
        assertNotEquals("(1, 2", v1.toString());
        assertNotEquals("120,300", v3.toString());
    }

    @Test
    public void precedesMethodTest() {
        assertTrue(v1.precedes(v2));
        assertTrue(v2.precedes(v4));
        assertTrue(v6.precedes(v3));
        assertFalse(v3.precedes(v4));
        assertFalse(v4.precedes(v5));
    }

    @Test
    public void followsMethodTest() {
        assertTrue(v4.follows(v1));
        assertTrue(v3.follows(v2));
        assertFalse(v3.follows(v4));
        assertTrue(v2.follows(v2));
        assertTrue(v4.follows(v6));
    }

    @Test
    public void upperRightMethodTest() {
        assertEquals(new Vector2d(8,4), v1.upperRight(v2));
        assertEquals(new Vector2d(125,300), v3.upperRight(v4));
        assertNotEquals(new Vector2d(1,8), v1.upperRight(v2));
        assertNotEquals(new Vector2d(125, 127), v3.upperRight(v4));
    }

    @Test
    public void lowerLeftMethodTest() {
        assertEquals(new Vector2d(120,128), v3.lowerLeft(v4));
        assertEquals(new Vector2d(1,2), v2.lowerLeft(v1));
        assertNotEquals(new Vector2d(-10,-8), v6.lowerLeft(v3));
        assertNotEquals(new Vector2d(4,8), v2.lowerLeft(v4));
    }

    @Test
    public void addMethodTest() {
        assertEquals(new Vector2d(9,6), v1.add(v2));
        assertEquals(new Vector2d(133,132), v5.add(v4));
        assertNotEquals(new Vector2d(225,400), v3.add(v4));
        assertNotEquals(new Vector2d(6,9), v1.add(v2));
    }

    @Test
    public void subtractMethodTest() {
        assertEquals(new Vector2d(-11,-7), v6.subtract(v1));
        assertEquals(new Vector2d(-7,-2), v1.subtract(v2));
        assertEquals(new Vector2d(7,2), v2.subtract(v1));
        assertNotEquals(new Vector2d(-7,-2), v2.subtract(v1));
    }

    @Test
    public void oppositeMethodTest() {
        assertEquals(new Vector2d(-1,-2), v1.opposite());
        assertEquals(new Vector2d(10,5), v6.opposite());
        assertNotEquals(new Vector2d(-10,5), v6.opposite());
        assertNotEquals(new Vector2d(120,300), v3.opposite());
    }
}
