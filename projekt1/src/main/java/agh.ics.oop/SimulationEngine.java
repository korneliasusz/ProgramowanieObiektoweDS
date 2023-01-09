package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SimulationEngine implements IEngine, Runnable {

    public EvolutionMap map;
    private Vector2d[] initialPositions;
    private App app;
    public int dailyGrown;

    public SimulationEngine(EvolutionMap map, Vector2d[] initialPositions, int dailyGrown, int energy, int numberOfGenes, App app) {
        this.map = map;
        this.initialPositions = initialPositions;
        this.app = app;
        this.dailyGrown = dailyGrown;
        for (Vector2d position : initialPositions) {
            Animal animal = new Animal(this.map, position, energy, numberOfGenes);
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
        }
    }

    @Override
    public void run() {
        int dayCounter = 0;
        while (dayCounter < 350)
        {
            dayCounter += 1;
            System.out.println(dayCounter);
            map.removeDeadAnimals(); // usunięcie martwych zwierząt z mapy
            if (map.animals.size() == 0) {
                System.out.println("Koniec symulacji - brak żywych zwierząt");
                app.endSimulation("Reason: there is no alive animal on map");
                break;
            }
            moveAnimals(); // skręt i przemieszczenie każdego zwierzęcia
            map.eatingGrass(); // konsumpcja roślin na których pola weszły zwierzęta
            map.copulation(); // rozmnażanie się najedzonych zwierząt znajdujących się na tym samym polu
            map.placeGrasses(dailyGrown); // TO DO wzrastanie nowych roślin na wybranych polach mapy
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            app.updateGrid();
        }
        if (dayCounter == 350) {
            System.out.println("Koniec symulacji - limit dni");
            app.endSimulation("Reason: maximum days reached");
        }
    }
}
