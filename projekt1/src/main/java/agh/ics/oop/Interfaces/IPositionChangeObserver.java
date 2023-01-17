package agh.ics.oop.Interfaces;

import agh.ics.oop.Elements.Animal;

public interface IPositionChangeObserver {
    void positionWillChange(Animal animal);
    void positionChanged(Animal animal);
}
