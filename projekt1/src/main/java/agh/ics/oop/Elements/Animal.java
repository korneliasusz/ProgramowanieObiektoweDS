package agh.ics.oop.Elements;

import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Map.EvolutionMap;
import agh.ics.oop.Map.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {
    public Vector2d position;
    public MapDirection orientation;
    public int energy;
    public Genes genes;
    public int activeGen;
    public int lifeLength;
    public int childrenCount;
    public int startEnergy;
    public int eatenGrasses;
    public int deathDay;
    public EvolutionMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    public boolean mutationFullyRandom = true;
    public boolean behaviour = true; // wariant zachowania: true - pełna predestynacja, false - nieco szaleństwa

    // konstruktor na potrzeby testów
    public Animal(Vector2d position, MapDirection orientation, Genes genes) {
        this.position = position;
        this.orientation = orientation;
        this.genes = genes;
    }

    // konstruktor dla dziecka
    public Animal(int energy, Vector2d position, boolean mutationFullyRandom, boolean behaviour, Genes genes, EvolutionMap map) {
        this.energy = energy;
        this.genes = genes;
        this.position = position;
        this.orientation = MapDirection.randomOrientation();
        this.activeGen = 0;
        this.map = map;
        this.lifeLength = 0;
        this.childrenCount = 0;
        this.startEnergy = this.energy;
        this.mutationFullyRandom = mutationFullyRandom;
        this.behaviour = behaviour;
        this.eatenGrasses = 0;
        addObserver((IPositionChangeObserver) map);
    }

    // konstruktor inicjalizujący
    public Animal(EvolutionMap map, Vector2d position, boolean mutationFullyRandom, boolean behaviour, int energy, int numOfGenes) {
        this.position = position;
        this.energy = energy;
        this.orientation = MapDirection.randomOrientation();
        this.genes = new Genes(numOfGenes);
        this.activeGen = 0;
        this.map = map;
        this.lifeLength = 0;
        this.childrenCount = 0;
        this.startEnergy = this.energy;
        this.mutationFullyRandom = mutationFullyRandom;
        this.behaviour = behaviour;
        this.eatenGrasses = 0;
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

    public void rotate() {
        for (int i = 0; i < this.genes.genes_array[this.activeGen]; i++) {
            this.orientation = this.orientation.next();
        }
    }
    private void getActiveGen() {
        if (behaviour) // zwierzak zawsze wykonuje kolejno geny, jeden po drugim
        {
            if (this.activeGen == (this.genes.numberOfGenes - 1)) {
                this.activeGen = 0;
            } else {
                this.activeGen += 1;
            }
        } else { // w 80% przypadków zwierzak po wykonaniu genu aktywuje gen następujący zaraz po nim, w 20% przypadków przeskakuje jednak do innego, losowego genu
            Random rand = new Random();
            int randInt = rand.nextInt(100) + 1;
            if (randInt <= 80) {
                if (this.activeGen == (this.genes.numberOfGenes - 1)) {
                    this.activeGen = 0;
                } else {
                    this.activeGen += 1;
                }
            } else {
                this.activeGen = rand.nextInt(7);
            }
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

    public int getEnergy() {
        return this.energy;
    }

    public int getLifeLength() {
        return this.lifeLength;
    }

    public int getChildrenCount() {
        return  this.childrenCount;
    }

    @Override
    public String toString() {
        return String.valueOf(energy);
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        if (this.energy <= 0.33 * startEnergy) {
            return "src/main/resources/animal4.png";
        } else if (this.energy >= 0.66 * startEnergy) {
            return "src/main/resources/animal1.png";
        } else {
            return "src/main/resources/animal3.png";
        }
    }
}
