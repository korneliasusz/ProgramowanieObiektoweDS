package agh.ics.oop.Map;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    private SortedSet<Vector2d> axisX = new TreeSet<>(Comparator.comparingInt(Vector2d::getX).thenComparingInt(Vector2d::getY));
    private SortedSet<Vector2d> axisY = new TreeSet<>(Comparator.comparingInt(Vector2d::getY).thenComparingInt(Vector2d::getX));
    @Override
    public void positionWillChange(Animal animal) {
        remove(animal.position);
    }

    @Override
    public void positionChanged(Animal animal) {
        add(animal.position);
    }

    public void addObject(IMapElement object) {
        add(object.getPosition());
    }

    public void removeObject(IMapElement object) { remove(object.getPosition());}
    private void add(Vector2d position) {
        axisX.add(position);
        axisY.add(position);
    }

    private void remove(Vector2d position) {
        axisX.remove(position);
        axisY.remove(position);
    }
}
