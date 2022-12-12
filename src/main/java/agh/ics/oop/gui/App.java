package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.awt.*;
import java.util.List;

import javafx.scene.control.Label;

public class App extends Application {

    private AbstractWorldMap map;
    private GridPane gridPane;
    private  int width = 400;
    private int height = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        // header
        drawHeader(lowerLeft, upperRight);
        // bok
        for (int i = upperRight.y; i >= lowerLeft.y; i--) {
            Label label = new Label(String.format("%d", i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, upperRight.y - i + 1);
            for (int j = lowerLeft.x; j <= upperRight.x; j++) {
                drawObject(new Vector2d(j, i), lowerLeft, upperRight);
            }
        }

        for (int i = lowerLeft.x; i <= upperRight.x + 1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width / (upperRight.x - lowerLeft.x + 2)));
        }
        for (int i = lowerLeft.y; i <= upperRight.y + 1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(height / (upperRight.y - lowerLeft.y + 2)));
        }

        Scene scene = new Scene(gridPane,width, height);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        List<MoveDirection> directions = new OptionsParser().parse(args);
        map = new GrassField(5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);
    }

    private void drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        Label label = new Label("y\\x");
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        for (int i = lowerLeft.x; i < upperRight.x + 1; i++) {
            label = new Label(String.format("%d", i));
            gridPane.add(label, i - lowerLeft.x + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);

        }
    }

    private void drawObject(Vector2d currentPosition, Vector2d lowerLeft, Vector2d upperRight) {
        if (this.map.isOccupied(currentPosition)) {
            Object object = this.map.objectAt(currentPosition);
            if (object != null) {
                Label label = new Label(object.toString());
                gridPane.add(label, currentPosition.getX() - lowerLeft.x + 1, upperRight.y - currentPosition.getY() + 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
    }
}
