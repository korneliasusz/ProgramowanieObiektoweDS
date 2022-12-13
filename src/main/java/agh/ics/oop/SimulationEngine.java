package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.*;


public class SimulationEngine implements IEngine, Runnable {

    private List<MoveDirection> directions;
    private IWorldMap map;
    private Vector2d[] initialPositions;

    private ArrayList<Animal> animals = new ArrayList<>();
    public MapVisualizer mapVisualizer;
    private int moveDelay;
    private App app;

    public SimulationEngine(List<MoveDirection> directions, IWorldMap map, Vector2d[] initialPositions) {
        this.directions = directions;
        this.map = map;
        this.initialPositions = initialPositions;
        this.moveDelay = moveDelay;
        this.app = app;

        for (Vector2d initialPosition : initialPositions) {
            Animal animal = new Animal(map, initialPosition);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    public SimulationEngine(IWorldMap map, Vector2d[] initialPositions, App app, int moveDelay) {
        this.map = map;
        this.initialPositions = initialPositions;
        this.moveDelay = moveDelay;
        this.app = app;

        for (Vector2d initialPosition : initialPositions) {
            Animal animal = new Animal(map, initialPosition);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    public SimulationEngine(List<MoveDirection> directions, IWorldMap map, Vector2d[] initialPositions, App app, int moveDelay) {
        this.directions = directions;
        this.map = map;
        this.initialPositions = initialPositions;
        this.moveDelay = moveDelay;
        this.app = app;

        for (Vector2d initialPosition : initialPositions) {
            Animal animal = new Animal(map, initialPosition);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    @Override
    public void run() {
        int animalsSize = animals.size();
        int animalID = 0;
        for (MoveDirection direction : directions) {
            System.out.println(map);
            animals.get(animalID).move(direction);
            animalID += 1;
            if (animalID == animalsSize) {
                animalID = 0;
            }
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> app.updateGrid());
        }
    }

    public void setDirections(List<MoveDirection> directions) {
        this.directions = directions;
    }
}
