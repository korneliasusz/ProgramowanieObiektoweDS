package agh.ics.oop;

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
        if (direction == MoveDirection.FORWARD && position.x < 4 && position.y < 4) {
            position = position.add(orientation.toUnitVector());
        }
        else if (direction == MoveDirection.BACKWARD && position.x > 0 && position.y > 0) {
            position = position.subtract(orientation.toUnitVector());
        }
        else if (direction == MoveDirection.LEFT) {
            orientation = orientation.previous();
        }
        else if (direction == MoveDirection.RIGHT) {
            orientation = orientation.next();
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
