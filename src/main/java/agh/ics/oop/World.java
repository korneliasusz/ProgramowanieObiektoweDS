package agh.ics.oop;


import java.util.Arrays;
import java.util.List;

public class World {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        System.out.println();
        List<MoveDirection> directions = new OptionsParser().parse(args);
        //IWorldMap map = new RectangularMap(10, 5);
        IWorldMap map = new GrassField(20);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }

}

