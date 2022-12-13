package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class App extends Application {

    private AbstractWorldMap map;
    private GridPane gridPane;
    private  int width = 500;
    private int height = 500;
    private int moveDelay = 900;
    SimulationEngine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        createGrid();

        Button startButton = new Button("Start");
        TextField textField = new TextField();
        HBox hBox = new HBox(textField, startButton);
        VBox vBox = new VBox(hBox, gridPane);

        startButton.setOnAction(event -> startEvent(textField.getText()));

        Scene scene = new Scene(vBox, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startEvent(String directions) {
        String[] directionsString = directions.split(" ");
        List<MoveDirection> directionList = new OptionsParser().parse(directionsString);
        engine.setDirections(directionList);
        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
    }

    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        List<MoveDirection> directions = new OptionsParser().parse(args);
        map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        engine = new SimulationEngine(map, positions, this, moveDelay);
        // IEngine engine = new SimulationEngine(directions, map, positions, this, moveDelay);
        // Thread thread = new Thread((Runnable) engine);
        // thread.start();
        System.out.println(map);
    }

    private void createGrid() {
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
            gridPane.getColumnConstraints().add(new ColumnConstraints((width - 100) / (upperRight.x - lowerLeft.x + 2)));
        }
        for (int i = lowerLeft.y; i <= upperRight.y + 1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints((height - 100) / (upperRight.y - lowerLeft.y + 2)));
        }
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
            IMapElement object = (IMapElement) this.map.objectAt(currentPosition);
            if (object != null) {
                Label label = new Label(object.toString());
                GuiElementBox guiElementBox = new GuiElementBox(object);
                gridPane.add(guiElementBox.vBox, currentPosition.getX() - lowerLeft.x + 1, upperRight.y - currentPosition.getY() + 1);
                GridPane.setHalignment(guiElementBox.vBox, HPos.CENTER);
            }
        }
    }

    public void updateGrid() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(false);
        createGrid();
        gridPane.setGridLinesVisible(true);
    }
}
