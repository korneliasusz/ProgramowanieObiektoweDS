package agh.ics.oop.Simulation;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Map.EvolutionMap;
import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Gui.App;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SimulationEngine implements IEngine, Runnable {

    public EvolutionMap map;
    private final Vector2d[] initialPositions;
    private final App app;
    public int dailyGrown;
    public boolean paused;
    public SimulationStatistics simulationStatistics;
    public AnimalStatistics animalStatistics;

    public SimulationEngine(EvolutionMap map, Vector2d[] initialPositions, int dailyGrown, int energy, int numberOfGenes, boolean mutationFullyRandom, boolean behaviour, App app) {
        this.map = map;
        this.initialPositions = initialPositions;
        this.app = app;
        this.dailyGrown = dailyGrown;
        this.simulationStatistics = new SimulationStatistics(this.map);
        this.animalStatistics = null;
        this.app.animalTracking = false;
        this.app.trackedAnimal = null;
        for (Vector2d position : initialPositions) {
            Animal animal = new Animal(this.map, position, mutationFullyRandom, behaviour, energy, numberOfGenes);
            map.place(animal);
        }
    }

    public void moveAnimals() {
        Set<Animal> animalSet = new HashSet<>();
        for (ArrayList<Animal> animal_list : map.animals.values()) {
            animalSet.addAll(animal_list);
        }

        for (Animal animal : animalSet) {
            animal.move();
            animal.energy -= 1; // zmniejszenie energii zwięrzęcia każdego dnia
            animal.lifeLength += 1; // zwiększenie długości życia zwierzęcia każdego dnia
        }
    }

    @Override
    public void run() {
        while (this.simulationStatistics.daysCounter < 350)
        {
            this.simulationStatistics.updateStatistics(this.map);
            app.updateStatistics();
            app.updateGrid();
            map.removeDeadAnimals(this.simulationStatistics.daysCounter); // usunięcie martwych zwierząt z mapy
            if (app.simulationStarted && map.animals.size() == 0) {
                System.out.println("Koniec symulacji - brak żywych zwierząt");
                app.endSimulation("Reason: there is no alive animal on map");
                break;
            }
            moveAnimals(); // skręt i przemieszczenie każdego zwierzęcia
            map.eatingGrass(); // konsumpcja roślin na których pola weszły zwierzęta
            map.copulation(); // rozmnażanie się najedzonych zwierząt znajdujących się na tym samym polu
            map.placeGrasses(dailyGrown); // wzrastanie nowych roślin na wybranych polach mapy
            try {
                Thread.sleep(300);
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (app.trackedAnimal != null) {
                if (this.animalStatistics == null) {
                    this.animalStatistics = new AnimalStatistics(app.trackedAnimal);
                }
                animalStatistics.updateAnimalStatistics(app.trackedAnimal);
            }
            if (app.saveStatistics) {
                app.writeStatistics();
            }
        }
        if (this.simulationStatistics.daysCounter == 350) {
            System.out.println("Koniec symulacji - limit dni");
            app.endSimulation("Reason: maximum days reached");
        }
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }
}
