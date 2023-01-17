package agh.ics.oop.Elements;

import agh.ics.oop.Interfaces.IMapElement;


public class Grass implements IMapElement {
    public Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/green.jpg";
    }

}
