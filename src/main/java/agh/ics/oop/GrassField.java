package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private int grassInt;
    private ArrayList<Grass> grasses = new ArrayList<>();

    public GrassField(int grassInt) {
        this.grassInt = grassInt;
        placeGrasses();
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
            grasses.add(new Grass(position));
        }
    }

   private void addNewGrass() {
        Vector2d position = new Vector2d(generateRandInt(), generateRandInt());
        while (grasses.size() < grassInt) {
            if (!isOccupied(position)) {
                grasses.add(new Grass(position));
            }
            position = new Vector2d(generateRandInt(), generateRandInt());
        }
    }

    @Override
    public boolean place(Animal animal) {
        if (!super.place(animal)) {
            if (!objectAt(animal.getPosition()).getClass().equals(Animal.class)) {
                animals.put(animal.getPosition(), animal);
                return true;
            } else if (objectAt(animal.getPosition()).getClass().equals(Grass.class)) {
                animals.put(animal.getPosition(), animal);
                addNewGrass();
                return true;
            }
        } else {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;

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
    protected Vector2d getUpperRight() {
        Vector2d upperRight;
        if (!animals.isEmpty()) {
            Animal animal = (Animal)animals.values().toArray()[0];
            upperRight = animal.getPosition();
        } else if (!grasses.isEmpty()) {
            upperRight = grasses.get(0).getPosition();
        } else {
            upperRight = new Vector2d(10,10);
        }

        for (Animal animal : animals.values()) {
            upperRight = upperRight.upperRight(animal.getPosition());
        }

        for (Grass grass : grasses) {
            upperRight = upperRight.upperRight(grass.getPosition());
        }

        return upperRight;
    }

    @Override
    protected Vector2d getLowerLeft() {
            Vector2d lowerLeft;
            if (!animals.isEmpty()) {
                //lowerLeft = animals.get(0).getPosition();
                Animal animal = (Animal)animals.values().toArray()[0];
                lowerLeft = animal.getPosition();
            } else if (!grasses.isEmpty()) {
                lowerLeft = grasses.get(0).getPosition();
            } else {
                lowerLeft = new Vector2d(0,0);
            }

            for (Animal animal : animals.values()) {
                lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
            }

            for (Grass grass : grasses) {
                lowerLeft = lowerLeft.lowerLeft(grass.getPosition());
            }

            return lowerLeft;
    }

}
