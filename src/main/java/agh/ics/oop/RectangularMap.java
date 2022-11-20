package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap extends AbstractWorldMap {

    private final int width;
    private final int height;


    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (super.canMoveTo(position) && position.x <= this.width && position.x >= 0 && position.y <= this.height && position.y >= 0) {
            return true;
        }
        return false;
    }

    @Override
    protected Vector2d getLowerLeft() {
        return new Vector2d(0,0);
    }

    @Override
    protected Vector2d getUpperRight() {
        return new Vector2d(width, height);
    }


}
