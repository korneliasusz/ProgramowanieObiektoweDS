package agh.ics.oop;

import java.util.*;


public class SimulationEngine implements IEngine {

    private List<MoveDirection> directions;
    private IWorldMap map;
    private Vector2d[] initialPositions;

    private ArrayList<Animal> animals = new ArrayList<>();
    public MapVisualizer mapVisualizer;

    public SimulationEngine(List<MoveDirection> directions, IWorldMap map, Vector2d[] initialPositions) {
        this.directions = directions;
        this.map = map;
        this.initialPositions = initialPositions;

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
        // new SwingAnimation(map);
        for (MoveDirection direction : directions) {
            System.out.println(map);
            // new SwingAnimation(map);
            animals.get(animalID).move(direction);
            animalID += 1;
            if (animalID == animalsSize) {
                animalID = 0;
            }
        }
    }

}
