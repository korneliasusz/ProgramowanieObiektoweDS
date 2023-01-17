package agh.ics.oop;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Genes;
import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Map.EvolutionMap;
import agh.ics.oop.Map.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void rotateTest() {
        Animal animal = new Animal(new Vector2d(2,2), MapDirection.NORTH, new Genes());
        animal.genes.numberOfGenes = 8;
        animal.genes.genes_array = new int[]{0, 1, 2, 3, 4, 5, 6, 7};

        animal.activeGen = 0;
        animal.rotate();
        assertEquals(animal.orientation, MapDirection.NORTH);

        animal.activeGen = 5;
        animal.rotate();
        assertEquals(animal.orientation, MapDirection.SOUTH_WEST);

        animal.activeGen = 7;
        animal.rotate();
        assertEquals(animal.orientation, MapDirection.SOUTH);
    }
}
