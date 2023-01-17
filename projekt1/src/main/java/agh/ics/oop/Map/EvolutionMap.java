package agh.ics.oop.Map;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Genes;
import agh.ics.oop.Elements.Grass;
import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IWorldMap;

import java.util.*;

public class EvolutionMap implements IWorldMap, IPositionChangeObserver {

    public int width;
    public int height;
    public int grassesInt;
    public int grassEnergy;
    public int animalsInt = 0;
    public Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    public Map<Vector2d, ArrayList<Animal>> deadAnimals = new HashMap<>();
    public Map<Vector2d, Grass> grasses = new HashMap<>();
    private final MapBoundary mapBoundary;
    private final int minCopulationEnergy;
    private final int copulationLostEnergy;
    private final int maxMutation;
    private final int minMutation;
    public boolean mapTypeGlobe = true; // domyślnie kula ziemska, opcjonalnie piekielny portal
    public boolean grassGrowthEquators = true; // domyślnie zalesione równiki

    public EvolutionMap(int width, int height, int grassesInt, int grassEnergy, int minCopulationEnergy, int copulationLostEnergy, int minMutation, int maxMutation, boolean mapTypeGlobe, boolean grassGrowthEquators) {
        this.width = width;
        this.height = height;
        this.grassesInt = grassesInt;
        this.grassEnergy = grassEnergy;
        this.mapBoundary = new MapBoundary();
        this.minCopulationEnergy = minCopulationEnergy;
        this.copulationLostEnergy = copulationLostEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.mapTypeGlobe = mapTypeGlobe;
        placeGrasses(grassesInt);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (mapTypeGlobe) {
            return position.y <= height && position.y >= 0;
        } else {
            return position.y <= height && position.y >= 0 && position.x >= 0 & position.x <= width;
        }
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.position)) {
            addAnimal(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        ArrayList<Animal> test = animals.get(position);
        if (test == null) {
            return grasses.get(position);
        }
        return test;
    }

    public void removeDeadAnimals(int simulationDay) {
        for (Animal animal : getAnimalsArray()) {
            if (animal.isDead()) {
                animal.deathDay = simulationDay;
                removeAnimal(animal);
                if (!deadAnimals.containsKey(animal.position)) {
                    deadAnimals.put(animal.position, new ArrayList<>());
                }
                deadAnimals.get(animal.position).add(animal);
            }
        }
    }

    public void placeGrasses(int count) {
        if (grassGrowthEquators) {
            if (grasses.size() < (this.width + 1) * (this.height + 1)) {
                HashSet<Vector2d> positions = new HashSet<>();
                while (positions.size() != count) {
                    Vector2d position;
                    int randInt = generateRandInt(0, 100);
                    if (randInt <= 80) {
                        position = new Vector2d(generateRandInt(0, width), generateRandInt((int) Math.round(height * 0.33), (int) Math.round(height * 0.66)));
                    } else if (randInt <= 90) {
                        position = new Vector2d(generateRandInt(0, width), generateRandInt(0, (int) Math.round(height * 0.33)));
                    } else {
                        position = new Vector2d(generateRandInt(0, width), generateRandInt((int) Math.round(height * 0.66), height));
                    }
                    if (!grasses.containsKey(position) && !animals.containsKey(position)) {
                        positions.add(position);
                    }
                }

                for (Vector2d position : positions) {
                    Grass grass = new Grass(position);
                    grasses.put(grass.position, grass);
                    mapBoundary.addObject(grass);
                }
            }
        } else {
            if (grasses.size() < (this.width + 1) * (this.height + 1)) {
                HashSet<Vector2d> positions = new HashSet<>();
                while (positions.size() != count) {
                    Vector2d position = new Vector2d(generateRandInt(0, width), generateRandInt(0, height));
                    int randInt = generateRandInt(0, 100);
                    if (randInt <= 80) {
                        if (!deadAnimals.containsKey(position)) {
                            positions.add(position);
                        }
                    } else {
                        if (deadAnimals.containsKey(position)) {
                            positions.add(position);
                        }
                    }

                    if (!grasses.containsKey(position) && !animals.containsKey(position)) {
                        positions.add(position);
                    }
                }

                for (Vector2d position : positions) {
                    Grass grass = new Grass(position);
                    grasses.put(grass.position, grass);
                    mapBoundary.addObject(grass);
                }
            }
        }

    }

    public void eatingGrass() {
        ArrayList<Grass> eaten_grasses = new ArrayList<>();
        Animal animal = null;
        for (ArrayList<Animal> animalArrayList : animals.values()) {
            if (animalArrayList.size() == 1) {
                animal = animalArrayList.get(0);

            } else if (animalArrayList.size() > 1) {
                animalArrayList.sort(Comparator.comparing(Animal::getEnergy, Comparator.reverseOrder())
                        .thenComparing(Animal::getLifeLength)
                        .thenComparing(Animal::getChildrenCount));
                animal = animalArrayList.get(0);
            }
            if (animal != null) {
                for (Grass grass : grasses.values()) {
                    if (animal.position.equals(grass.position)) {
                        animal.eatenGrasses += 1;
                        eaten_grasses.add(grass);
                        animal.energy += this.grassEnergy;
                    }
                }
            }
        }

        for (Grass grass : eaten_grasses)  {
            grasses.remove(grass.position);
            mapBoundary.removeObject(grass);
        }
    }

    public void copulation() {
        ArrayList<Animal> children = new ArrayList<>();
        for (ArrayList<Animal> animalArrayList : animals.values()) {
            if (animalArrayList.size() == 2) {
                Animal mother = animalArrayList.get(0);
                Animal father = animalArrayList.get(1);
                if (mother.energy >= minCopulationEnergy && father.energy >= minCopulationEnergy) {
                    Animal child = new Animal(2*minCopulationEnergy, mother.position, mother.mutationFullyRandom, mother.behaviour, childGenes(mother, father), this);
                    child.activeGen = generateRandInt(0, 7);
                    child.mutation(minMutation, maxMutation);
                    children.add(child);
                    // Przy reprodukcji rodzice tracą na rzecz młodego pewną część swojej energii
                    // - ta energia będzie rónocześnie stanowić startową energię ich potomka
                    mother.energy -= copulationLostEnergy;
                    father.energy -= copulationLostEnergy;
                    mother.childrenCount += 1;
                    father.childrenCount += 1;
                }
            }
        }
        for (Animal child : children) {
            place(child);
        }
    }

    private Genes childGenes(Animal mother, Animal father) {
        double motherPart = (double) mother.energy / (double) (mother.energy + father.energy);
        double fatherPart = (double) father.energy / (double) (mother.energy + father.energy);
        Genes genes = new Genes();
        genes.genes_array = new int[mother.genes.numberOfGenes];
        genes.numberOfGenes = genes.genes_array.length;
        //  losowana jest strona genotypu, z której zostanie wzięta część osobnika silniejszego
        // 0 - lewa, 1 - prawa
        Random rand = new Random();
        int part = rand.nextInt(2);
        if (motherPart >= fatherPart) {
            if (part == 0) {
                for (int i = 0; i < motherPart * mother.genes.numberOfGenes; i++) {
                    genes.genes_array[i] = mother.genes.genes_array[i];
                }
                for (int i = father.genes.numberOfGenes - 1; i >= father.genes.numberOfGenes - fatherPart * father.genes.numberOfGenes; i--) {
                    genes.genes_array[i] = father.genes.genes_array[i];
                }
            } else if (part == 1) {
                for (int i = 0; i < fatherPart * father.genes.numberOfGenes; i++) {
                    genes.genes_array[i] = father.genes.genes_array[i];
                }
                for (int i = mother.genes.numberOfGenes - 1; i >= mother.genes.numberOfGenes - motherPart * mother.genes.numberOfGenes; i--) {
                    genes.genes_array[i] = mother.genes.genes_array[i];
                }
            }
        } else {
            if (part == 1) {
                for (int i = 0; i < motherPart * mother.genes.numberOfGenes; i++) {
                    genes.genes_array[i] = mother.genes.genes_array[i];
                }
                for (int i = father.genes.numberOfGenes - 1; i > father.genes.numberOfGenes - fatherPart * father.genes.numberOfGenes; i--) {
                    genes.genes_array[i] = father.genes.genes_array[i];
                }
            } else if (part == 0) {
                for (int i = 0; i < fatherPart * father.genes.numberOfGenes; i++) {
                    genes.genes_array[i] = father.genes.genes_array[i];
                }
                for (int i = mother.genes.numberOfGenes - 1; i > mother.genes.numberOfGenes - motherPart * mother.genes.numberOfGenes; i--) {
                    genes.genes_array[i] = mother.genes.genes_array[i];
                }
            }
        }
        return genes;
    }
    private int generateRandInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void addAnimal(Animal animal) {
        if (!animals.containsKey(animal.position)) {
            animals.put(animal.position, new ArrayList<>());
        }
        animals.get(animal.position).add(animal);
        animalsInt += 1;
        mapBoundary.addObject(animal);
    }

    private void removeAnimal(Animal animal) {
        animals.get(animal.position).remove(animal);
        if (animals.get(animal.position).isEmpty()) {
            animals.remove(animal.position);
        }
        animalsInt -= 1;
        mapBoundary.removeObject(animal);
    }

    private ArrayList<Animal> getAnimalsArray() {
        ArrayList<Animal> animals_tmp = new ArrayList<>();
        for (ArrayList<Animal> animals_list : animals.values()) {
            animals_tmp.addAll(animals_list);
        }
        return animals_tmp;
    }

    @Override
    public void positionWillChange(Animal animal) {
        removeAnimal(animal);
    }

    @Override
    public void positionChanged(Animal animal) {
        addAnimal(animal);
    }
    
    public int emptyCells() {
        int emptyCells = 0;
        for (int i = 0; i <= this.width; i++) {
            for (int j = 0; j <= this.height; j++) {
                if (objectAt(new Vector2d(i, j)) == null) {
                    emptyCells += 1;
                }
            }
        }
        return emptyCells;
    }

    public float getAverageAnimalsEnergy() {
        float averageEnergy = 0;
        for (ArrayList<Animal> animalArrayList : animals.values()) {
            for (Animal animal : animalArrayList) {
                averageEnergy += animal.energy;
            }
        }
        return (float) Math.round(100 * averageEnergy / animalsInt) / 100;
    }

    public float getAverageLifeLength() {
        float averageLifeLength = 0;
        int deadAnimalsCount = 0;
        for (ArrayList<Animal> animalArrayList : deadAnimals.values()) {
            for (Animal animal : animalArrayList) {
                averageLifeLength += animal.lifeLength;
                deadAnimalsCount += 1;
            }
        }
        return (float) Math.round(100 * averageLifeLength / deadAnimalsCount) / 100;
    }
}
