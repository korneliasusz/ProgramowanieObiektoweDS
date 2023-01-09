package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionWillChange(Animal animal);
    void positionChanged(Animal animal);
}
