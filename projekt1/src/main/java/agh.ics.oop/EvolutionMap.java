package agh.ics.oop;

import java.util.*;

public class EvolutionMap implements IWorldMap, IPositionChangeObserver {

    public int width;
    public int height;
    public int grassesInt;
    public int grassEnergy;
    public Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    public Map<Vector2d, Grass> grasses = new HashMap<>();
    private MapBoundary mapBoundary;
    private int minCopulationEnergy;
    private int copulationLostEnergy;
    private int maxMutation;
    private int minMutation;
    public boolean mapTypeGlobe = true; // domyślnie kula ziemska, opcjonalnie piekielny portal

    // konstruktor z wartościami domyślnymi
//    public EvolutionMap() {
//        this.width = 10;
//        this.height = 10;
//        this.grassesInt = 10;
//        this.grassEnergy = 5;
//        this.mapBoundary = new MapBoundary();
//        this.minCopulationEnergy = 75;
//        placeGrasses(grassesInt);
//    }

    public EvolutionMap(int width, int height, int grassesInt, int grassEnergy, int minCopulationEnergy, int copulationLostEnergy, int minMutation, int maxMutation, boolean mapTypeGlobe) {
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

    // TO DO ograniczenie poruszania się po mapie
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (mapTypeGlobe) {
            return position.y <= height && position.y >= 0;
        } else {
            return position.y <= height && position.y >= 0 && position.x >= 0 & position.x <= width;
        }
//        return position.y <= height && position.y >= 0 && position.x >= 0 & position.x <= width;
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

    public void removeDeadAnimals() {
        for (Animal animal : getAnimalsArray()) {
            if (animal.isDead()) {
                removeAnimal(animal);
            }
        }
    }

    // rozmieszcza rosliny rownomiernie - nie bierze pod uwage dzungli
    public void placeGrasses(int count) {
        if (grasses.size() < (this.width + 1) * (this.height + 1)) {
            HashSet<Vector2d> positions = new HashSet<>();

            while (positions.size() != count) {
                Vector2d position = new Vector2d(generateRandInt(width), generateRandInt(height));
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

    public void eatingGrass() {
        ArrayList<Grass> eaten_grasses = new ArrayList<>();
        for (Animal animal : getAnimalsArray()) {
            for (Grass grass : grasses.values()) {
                if (animal.position.equals(grass.position)) {
                    eaten_grasses.add(grass);
                    animal.energy += this.grassEnergy;
                }
            }
        }
        for (Grass grass : eaten_grasses)  {
            grasses.remove(grass.position);
            mapBoundary.removeObject(grass);
        }
        // placeGrasses(eaten_grasses.size());
    }

    public void copulation() {
        ArrayList<Animal> children = new ArrayList<>();
        for (ArrayList<Animal> animalArrayList : animals.values()) {
            if (animalArrayList.size() == 2) {
                Animal mother = animalArrayList.get(0);
                Animal father = animalArrayList.get(1);
                if (mother.energy >= minCopulationEnergy && father.energy >= minCopulationEnergy) {
                    Animal child = new Animal(2*minCopulationEnergy, childGenes(mother, father), this);
                    child.mutation(minMutation, maxMutation);
                    children.add(child);
                    // Przy reprodukcji rodzice tracą na rzecz młodego pewną część swojej energii
                    // - ta energia będzie rónocześnie stanowić startową energię ich potomka
                    mother.energy -= minCopulationEnergy;
                    father.energy -= copulationLostEnergy;
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
    private int generateRandInt(int max) {
        Random rand = new Random();
        return rand.nextInt(max+1);
    }

    private void addAnimal(Animal animal) {
        if (!animals.containsKey(animal.position)) {
            animals.put(animal.position, new ArrayList<>());
        }
        animals.get(animal.position).add(animal);
        mapBoundary.addObject(animal);
    }

    private void removeAnimal(Animal animal) {
        animals.get(animal.position).remove(animal);
        if (animals.get(animal.position).isEmpty()) {
            animals.remove(animal.position);
        }
        mapBoundary.removeObject(animal);
    }

    private ArrayList<Animal> getAnimalsArray() {
        ArrayList<Animal> animals_tmp = new ArrayList<>();
        for (ArrayList<Animal> animals_list : animals.values()) {
            for (Animal animal : animals_list) {
                animals_tmp.add(animal);
            }
        }
        return animals_tmp;
    }

    public int getAnimalsCount() {
        return this.animals.size();
    }

    public int getGrassesCount() {
        return this.grasses.size();
    }
    @Override
    public void positionWillChange(Animal animal) {
        removeAnimal(animal);
    }

    @Override
    public void positionChanged(Animal animal) {
        addAnimal(animal);
    }
}
