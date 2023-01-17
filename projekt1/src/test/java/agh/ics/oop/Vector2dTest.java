package agh.ics.oop;

import agh.ics.oop.Elements.Vector2d;
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
    public void equalsMethod() {
        assertEquals(v2, v5);
        assertNotEquals(v2, v4);
        assertNotEquals(v5, v3);
        assertNotEquals(v6, v2);
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
    public void addMethodTest() {
        assertEquals(new Vector2d(9,6), v1.add(v2));
        assertEquals(new Vector2d(133,132), v5.add(v4));
        assertNotEquals(new Vector2d(225,400), v3.add(v4));
        assertNotEquals(new Vector2d(6,9), v1.add(v2));
    }
}
