package agh.ics.oop;

public class Grass extends AbstractWorldMapElement {
    //private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }

    @Override
    public String getElementName() {
        return "Trawa";
    }

    @Override
    public String toString() {
        return "*";
    }
}
