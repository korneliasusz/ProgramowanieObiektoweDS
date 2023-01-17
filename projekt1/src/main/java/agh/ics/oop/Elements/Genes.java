package agh.ics.oop.Elements;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    public int numberOfGenes;
    public int[] genes_array;

    public Genes() {
    }

    public Genes(int numberOfGenes) {
        this.numberOfGenes = numberOfGenes;
        this.genes_array = new int[numberOfGenes];
        generateRandomGen();
    }

    private void generateRandomGen() {
        Random rand = new Random();
        for (int i = 0; i < numberOfGenes; i++) {
            genes_array[i] = rand.nextInt(8);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(genes_array);
    }
}
