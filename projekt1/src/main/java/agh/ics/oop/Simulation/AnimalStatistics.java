package agh.ics.oop.Simulation;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Genes;

public class AnimalStatistics {

    public Genes genes;
    public int activeGen;
    public int energy;
    public int eatenGrasses;
    public int childrenCount;
    public int daysAlive;
    public int deathDay;
    public int animalsStatisticsCounter = 0;

    public AnimalStatistics(Animal animal) {
        this.genes = animal.genes;
        this.activeGen = animal.activeGen;
        this.energy = animal.energy;
        this.eatenGrasses = animal.eatenGrasses;
        this.childrenCount = animal.childrenCount;
        this.daysAlive = animal.lifeLength;
        this.deathDay = animal.deathDay;
    }

    public void updateAnimalStatistics(Animal animal) {
        this.animalsStatisticsCounter += 1;
        this.genes = animal.genes;
        this.activeGen = animal.activeGen;
        this.energy = animal.energy;
        this.eatenGrasses = animal.eatenGrasses;
        this.childrenCount = animal.childrenCount;
        this.daysAlive = animal.lifeLength;
        this.deathDay = animal.deathDay;
    }
}
