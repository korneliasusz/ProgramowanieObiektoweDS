package agh.ics.oop;

import java.util.Vector;

public class Animal {
    protected MapDirection orientation = MapDirection.NORTH;
    protected Vector2d position = new Vector2d(2,2);

    public Animal(MapDirection orientation, Vector2d position) {
        this.orientation = orientation;
        this.position = position;
    }

    public Animal() {
    }
    
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }


    public void move(MoveDirection direction) {
        Vector2d v_test = position;
        if (direction == MoveDirection.FORWARD) {
            v_test = position.add(orientation.toUnitVector());
        }
        else if (direction == MoveDirection.BACKWARD) {
            v_test = position.subtract(orientation.toUnitVector());
        }
        else if (direction == MoveDirection.LEFT) {
            orientation = orientation.previous();
        }
        else if (direction == MoveDirection.RIGHT) {
            orientation = orientation.next();
        }
        if (v_test.x <= 4 && v_test.x >= 0 && v_test.y <= 4 && v_test.y >= 0) {
            position = v_test;
        }

    }

    @Override
    public String toString() {
        return "{" +
                "Orientacja: " + orientation +
                ", Pozycja: " + position +
                '}';
    }
}
