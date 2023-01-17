package agh.ics.oop;


import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {

    public static void main(String[] args) {
        try {
            // args = new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            // System.out.println(Arrays.toString(args));
            // System.out.println();
            // List<MoveDirection> directions = new OptionsParser().parse(args);
            // IWorldMap map = new RectangularMap(10, 5);
            // IWorldMap map = new GrassField(5);
            // Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            // IEngine engine = new SimulationEngine(directions, map, positions);
            // engine.run();
            // System.out.println(map);

            // Application
            Application.launch(App.class, args);


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

