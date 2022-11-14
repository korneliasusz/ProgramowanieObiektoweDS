package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap {

    private final int width;
    private final int height;

    private ArrayList<Animal> animals = new ArrayList<>();
    private MapVisualizer mapVisualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.x <= this.width && position.x >= 0 && position.y <= this.height && position.y >= 0 && !isOccupied(position)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (objectAt(position) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(width,height));
    }
}
