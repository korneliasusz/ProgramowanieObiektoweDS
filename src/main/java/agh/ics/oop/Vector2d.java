package agh.ics.oop;

import java.util.Objects;
import java.util.Vector;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2d other) {
        if (this.x <= other.x && this.y <= other.y) {
            return true;
        }
        return false;
    }

    public boolean follows(Vector2d other) {
        if (this.x >= other.x && this.y >= other.y) {
            return true;
        }
        return false;
    }

    public Vector2d add(Vector2d other) {
        Vector2d vector2d = new Vector2d(this.x + other.x, this.y + other.y);
        return vector2d;
    }

    public Vector2d subtract(Vector2d other) {
        Vector2d vector2d = new Vector2d(this.x - other.x, this.y - other.y);
        return vector2d;
    }

    public Vector2d upperRight(Vector2d other) {
        /* if (vector2d.x <= other.x) {
            vector2d.x = other.x;
        }
        if (vector2d.y <= other.y) {
            vector2d.y = other.y;
        } */
        if (this.x <= other.x && this.y <= other.y) {
            return new Vector2d(other.x, other.y);
        } else if (this.x >= other.x && this.y <= other.y) {
            return new Vector2d(this.x, other.y);
        } else if (this.x <= other.x && this.y >= other.y) {
            return new Vector2d(other.x, this.y);
        }
        return this;
    }

    public Vector2d lowerLeft(Vector2d other) {
        /* if (vector2d.x >= other.x) {
            vector2d.x = other.x;
        }
        if (vector2d.y >= other.y) {
            vector2d.y = other.y;
        } */
        if (this.x >= other.x && this.y >= other.y) {
            return new Vector2d(other.x, other.y);
        } else if (this.x <= other.x && this.y >= other.y) {
            return new Vector2d(this.x, other.y);
        } else if (this.x >= other.x && this.y <= other.y) {
            return new Vector2d(other.x, this.y);
        }
        return this;
    }

    public Vector2d opposite() {
        Vector2d vector2d = new Vector2d(-this.x, -this.y);
        return vector2d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" +
                + x + ","
                + y +
                ')';
    }
}
