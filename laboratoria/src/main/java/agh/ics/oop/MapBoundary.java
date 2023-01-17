package agh.ics.oop;

import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    private SortedSet<Vector2d> axisX = new TreeSet<>(Comparator.comparingInt(Vector2d::getX).thenComparingInt(Vector2d::getY));
    private SortedSet<Vector2d> axisY = new TreeSet<>(Comparator.comparingInt(Vector2d::getY).thenComparingInt(Vector2d::getX));

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        remove(oldPosition);
        add(newPosition);
    }

    public void addObject(IMapElement object) {
        add(object.getPosition());
        System.out.println("x: " + axisX);
        System.out.println("y: " + axisY);
    }
    private void add(Vector2d position) {
        axisX.add(position);
        axisY.add(position);
    }

    private void remove(Vector2d position) {
        axisX.remove(position);
        axisY.remove(position);
    }
    public Vector2d getLowerLeft() {
        return new Vector2d(axisX.first().x, axisY.first().y);
    }

    public Vector2d getUpperRight() {
        return new Vector2d(axisX.last().x, axisY.last().y);
    }
}
