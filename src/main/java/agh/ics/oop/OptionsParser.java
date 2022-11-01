package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public OptionsParser() {

    }

    public List<MoveDirection> parse(String[] args) {
        List<MoveDirection> directions = new ArrayList<>();

        for (String i :args) {
            switch (i) {
                case "f":
                case "forward":
                    directions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                case "backward":
                    directions.add(MoveDirection.BACKWARD);
                    break;
                case "r":
                case "right":
                    directions.add(MoveDirection.RIGHT);
                    break;
                case "l":
                case "left":
                    directions.add(MoveDirection.LEFT);
                    break;
            }
        }
        return directions;
    }


}
