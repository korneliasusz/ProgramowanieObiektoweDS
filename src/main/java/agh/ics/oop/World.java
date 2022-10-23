package agh.ics.oop;


import java.util.Vector;

public class World {

    public static void main(String[] args) {
        MapDirection m1 = MapDirection.EAST;
        System.out.println(m1);
        System.out.println(m1.next());
        System.out.println(m1.previous());
        System.out.println(m1.toUnitVector());
    }
}