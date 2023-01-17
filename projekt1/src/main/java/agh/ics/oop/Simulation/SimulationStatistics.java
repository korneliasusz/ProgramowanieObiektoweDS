package agh.ics.oop.Simulation;

import agh.ics.oop.Map.EvolutionMap;

public class SimulationStatistics {
    public int daysCounter;
    public int grassesCounter;
    public int animalsCounter;
    public int emptyCells;
    public float averageEnergy;
    public float averageLifeLength;

    public SimulationStatistics(EvolutionMap map) {
        this.daysCounter = 0;
        this.grassesCounter = map.grassesInt;
        this.animalsCounter = map.animalsInt;
        this.emptyCells = map.emptyCells();
        this.averageEnergy = map.getAverageAnimalsEnergy();
        this.averageLifeLength = map.getAverageLifeLength();
    }

    public void updateStatistics(EvolutionMap map) {
      this.daysCounter += 1;
      this.grassesCounter = map.grasses.size();
      this.animalsCounter = map.animalsInt;
      this.emptyCells = map.emptyCells();
      this.averageEnergy = map.getAverageAnimalsEnergy();
      this.averageLifeLength = map.getAverageLifeLength();
    }
}
