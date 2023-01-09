package agh.ics.oop;


import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {
    protected Vector2d position;
    protected MapDirection orientation;
    public int energy;
    public Genes genes;
    public int activeGen;
    public EvolutionMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private boolean mutationFullyRandom = true;

    // konstruktor bezparametrowy, gdy nie są podane parametry
//    public Animal() {
//        this.position = new Vector2d(2,2);
//        this.energy = 100;
//        this.orientation = MapDirection.NORTH;
//        this.genes = new Genes(32);
//        this.activeGen = 0;
//        addObserver((IPositionChangeObserver) map);
//    }

    public Animal(int energy, Genes genes, EvolutionMap map) {
        this.energy = energy;
        this.genes = genes;
        this.position = new Vector2d(2,2);
        this.orientation = MapDirection.randomOrientation();
        this.activeGen = 0;
        this.map = map;
        addObserver((IPositionChangeObserver) map);
    }

//    public Animal(Vector2d position) {
//        this.position = position;
//        this.energy = 100;
//        this.orientation = MapDirection.NORTH;
//        this.genes = new Genes(32);
//        this.activeGen = 0;
//        addObserver((IPositionChangeObserver) map);
//    }

//    public Animal(IWorldMap map) {
//        this.position = new Vector2d(2,2);
//        this.energy = 100;
//        this.orientation = MapDirection.NORTH;
//        this.genes = new Genes(32);
//        this.activeGen = 0;
//        this.map = map;
//        addObserver((IPositionChangeObserver) map);
//    }

    public Animal(EvolutionMap map, Vector2d position, int energy, int numOfGenes) {
        this.position = position;
        this.energy = energy;
        this.orientation = MapDirection.randomOrientation();
        this.genes = new Genes(numOfGenes);
        this.activeGen = 0;
        this.map = map;
        addObserver((IPositionChangeObserver) map);
    }

    public void move() {
        this.positionWillChange(this);
        rotate();
        Vector2d v_test = this.position.add(orientation.toUnitVector());
        if (map.mapTypeGlobe) {
            if (v_test.x < 0) {
                v_test.x = map.width;
            } else if (v_test.x > map.width) {
                v_test.x = 0;
            }
        } else {
            if (!map.canMoveTo(v_test)) {
                Random rand = new Random();
                v_test = new Vector2d(rand.nextInt(map.width + 1), rand.nextInt(map.height + 1));
            }
        }
        if (map.canMoveTo(v_test)) {
            this.position = v_test;
        }
        this.positionChanged(this);
        getActiveGen();
    }

    private void rotate() {
        for (int i = 0; i < this.genes.genes_array[this.activeGen]; i++) {
            this.orientation = this.orientation.next();
        }
    }
    private void getActiveGen() {
        if (this.activeGen == (this.genes.numberOfGenes - 1)) {
            this.activeGen = 0;
        } else {
            this.activeGen += 1;
        }
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public void mutation(int min, int max) {
        Random rand = new Random();
        int genesToMutateInt = rand.nextInt((max - min) + 1) + min; // liczba genów, które ulegną mutacji
        ArrayList<Integer> genesToMutate = new ArrayList<>();
        for (int i = 0; i < genesToMutateInt; i++) {
            genesToMutate.add(rand.nextInt(genes.numberOfGenes));
        }

        if (genesToMutateInt != 0) {
            for (Integer i : genesToMutate) {
                if (mutationFullyRandom) {
                    this.genes.genes_array[i] = rand.nextInt(8);
                } else {
                    int randInt = rand.nextInt(2);
                    if (randInt == 0) {
                        this.genes.genes_array[i] -= 1;
                    } else {
                        this.genes.genes_array[i] += 1;
                    }
                }
            }
        }

    }
    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    private void positionWillChange(Animal animal) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionWillChange(animal);
        }
    }
    private void positionChanged(Animal animal) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(animal);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(energy);
//        return String.valueOf(orientation);
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/animal.png";
    }

    @Override
    public String getElementName() {
        return null;
    }
}
