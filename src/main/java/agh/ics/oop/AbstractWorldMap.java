package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    // protected ArrayList<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!isOccupied(position)) {
            return true;
        } else return !objectAt(position).getClass().equals(Animal.class);
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!isOccupied(animal.getPosition()) && canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        throw new IllegalArgumentException("Animal can't be placed at " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals.values()) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getLowerLeft(), getUpperRight());
    }

    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal movedAnimal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, movedAnimal);
    }
}
