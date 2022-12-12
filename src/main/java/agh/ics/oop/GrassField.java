package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private int grassInt;
    private ArrayList<Grass> grasses = new ArrayList<>();
    private MapBoundary mapBoundary;

    public GrassField(int grassInt) {
        this.grassInt = grassInt;
        this.mapBoundary = new MapBoundary();
        placeGrasses();
    }

    public GrassField(MapBoundary mapBoundary, int grassInt) {
        this.mapBoundary = mapBoundary;
        this.grassInt = grassInt;
    }

    private int generateRandInt() {
        Random rand = new Random();
        int max = (int) Math.sqrt(grassInt*10);
        return rand.nextInt(max+1);
    }

    protected void placeGrasses() {
        HashSet<Vector2d> positions = new HashSet<>();

        while (positions.size() != grassInt) {
            positions.add(new Vector2d(generateRandInt(),generateRandInt()));
        }

        for (Vector2d position : positions) {
            Grass grass = new Grass(position);
            grasses.add(grass);
            mapBoundary.addObject(grass);
        }
    }

   private void addNewGrass() {
        Vector2d position = new Vector2d(generateRandInt(), generateRandInt());
        while (grasses.size() < grassInt) {
            if (!isOccupied(position)) {
                Grass grass = new Grass(position);
                grasses.add(grass);
                mapBoundary.addObject(grass);
            }
            position = new Vector2d(generateRandInt(), generateRandInt());
        }
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!super.place(animal)) {
            if (!objectAt(animal.getPosition()).getClass().equals(Animal.class)) {
                animals.put(animal.getPosition(), animal);
                mapBoundary.addObject(animal);
                return true;
            } else if (objectAt(animal.getPosition()).getClass().equals(Grass.class)) {
                animals.put(animal.getPosition(), animal);
                addNewGrass();
                mapBoundary.addObject(animal);
                return true;
            }
        } else {
            animals.put(animal.getPosition(), animal);
            mapBoundary.addObject(animal);
            return true;
        }
        throw new IllegalArgumentException("Animal can't be placed at " + animal.getPosition());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (super.canMoveTo(position)) {
            for (int i = 0; i < grasses.size(); i++) {
                if (grasses.get(i).getPosition().equals(position)) {
                    grasses.remove(i);
                    this.addNewGrass();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals.values()) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        for (Grass grass : grasses) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    @Override
    public Vector2d getUpperRight() {
        return mapBoundary.getUpperRight();
    }

    @Override
    public Vector2d getLowerLeft() {
        return mapBoundary.getLowerLeft();
    }

}
