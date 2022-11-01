package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    Animal a1 = new Animal();
    Animal a2 = new Animal(MapDirection.EAST, new Vector2d(1,3));
    String[] directions = {"r", "f", "a", "l", "L", "f", "l", "b"};
    OptionsParser parser = new OptionsParser();
    List<MoveDirection> direct = List.of(MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.BACKWARD);

    @Test
    public void RightOrientationTest() {
        a2.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.SOUTH, a2.orientation);

        a1.move(MoveDirection.LEFT);
        a1.move(MoveDirection.LEFT);
        a1.move(MoveDirection.LEFT);
        assertEquals(MapDirection.EAST, a1.orientation);
    }

    @Test
    public void RightPositionTest() {
        a1.move(MoveDirection.LEFT);
        assertTrue(a1.isAt(new Vector2d(2,2)));
        a1.move(MoveDirection.FORWARD);
        assertTrue(a1.isAt(new Vector2d(1,2)));

        a2.move(MoveDirection.BACKWARD);
        assertFalse(a2.isAt(new Vector2d(1,3)));
    }

    @Test
    public void NotOutOfMapTest() {
        for (int i = 0; i < 5; i++) {
            a1.move(MoveDirection.FORWARD);
            a2.move(MoveDirection.BACKWARD);
        }
        assertTrue(a1.position.x <= 4 && a1.position.y <= 4);
        assertFalse(a2.position.x == -4 && a2.position.y == 3);
    }

    @Test
    public void RightDataParseTest() {
        assertEquals(direct, parser.parse(directions));
    }
}
