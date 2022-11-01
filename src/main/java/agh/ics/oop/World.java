package agh.ics.oop;


import java.util.List;
import java.util.Vector;

public class World {

    public static void main(String[] args) {
        Animal a1 = new Animal();
        Animal a2 = new Animal(MapDirection.EAST, new Vector2d(1,1));
        System.out.println("Zwierze 1: " + a1);
        System.out.println("Zwierze 2: " + a2 + "\n");

        System.out.println("Ruch zwirzecia 2 w tyl.");
        a2.move(MoveDirection.BACKWARD);
        System.out.println("Zwierze 2: " + a2 + "\n");

        System.out.println("Ruch zwierzecia 1 w prawo i trzy razy w przod.");
        a1.move(MoveDirection.RIGHT);
        a1.move(MoveDirection.FORWARD);
        a1.move(MoveDirection.FORWARD);
        a1.move(MoveDirection.FORWARD);
        System.out.println("Zwierze 1: " + a1);


        String[] tab = {"a", "b", "r", "left", "l", "f"};
        OptionsParser p1 = new OptionsParser();
        List<MoveDirection> directions = p1.parse(tab);
        System.out.println("\n" + directions);

        System.out.println();

        System.out.println("Zwierze 1: " + a1);
        System.out.println("Wykonanie ruchow z tablicy");
        for (MoveDirection md : directions) {
            a1.move(md);
        }
        System.out.println("Zwierze 1: " + a1);

    }

}

