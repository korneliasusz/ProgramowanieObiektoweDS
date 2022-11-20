package agh.ics.oop;

import java.util.Vector;

public class Animal extends AbstractWorldMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    //private Vector2d position = new Vector2d(2,2);
    private IWorldMap map;

    // konstruktor bezparametrowy nie ma sensu, poniewaz mapa nie ma automatycznie przypisanej wartosci,
    // tylko musi zostac przekazana jako paramter
    // nie ma rowniez potrzeby, aby w kontruktorze przekazywany byly parametr orientation
    // paramter orientation ma miec domyslnie zawsze wartosc NORTH

    public Animal(IWorldMap map) {
        this.map = map;
        position = new Vector2d(2,2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        position = initialPosition;
    }

    @Override
    public Vector2d getPosition() {
        return position;
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
        /*if (v_test.x <= 4 && v_test.x >= 0 && v_test.y <= 4 && v_test.y >= 0) {
            position = v_test;
        }
         */

        if (map.canMoveTo(v_test)) {
            position = v_test;
        }
    }

    @Override
    public String toString() {
        return orientation.toString();

    }
}
