package agh.ics.oop.Gui;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Elements.Grass;
import agh.ics.oop.Elements.Vector2d;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Map.EvolutionMap;
import agh.ics.oop.Simulation.SimulationEngine;
import com.opencsv.CSVWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class App extends Application{

    private GridPane gridPane;
    private final int width = 500;
    private final int height = 700;
    private EvolutionMap map;
    private SimulationEngine engine;
    private Stage newWindow;
    private Stage confWindow;
    private int mapWidth;
    private int mapHeight;
    private int animalsInt;
    private int startEnergy;
    private int grassInt;
    private int grassEnergy;
    private int dailyGrass;
    private int numberOfGenes;
    private int minCopEnergy;
    private int copLostEnergy;
    private int minMutation;
    private int maxMutation;
    private boolean mapTypeGlobe;
    private boolean mutationFullyRandom;
    private boolean behaviour;
    private String fileName;
    public VBox vboxDetails;
    public Animal trackedAnimal = null;
    public boolean animalTracking = false;
    public boolean saveStatistics = false;
    public boolean simulationStarted = false;
    public boolean grassGrowthEquators;
    private GuiElementBox guiElementBox;
    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane = new GridPane();

        // menu z parametrami
        GridPane settingsGrid = new GridPane();
        settingsGrid.setPadding(new Insets(10,10,10,10));
        settingsGrid.setHgap(10);
        settingsGrid.setVgap(10);
        settingsGrid.setAlignment(Pos.CENTER);

        // wysokość mapy
        Label mapHeight = new Label("Map height");
        TextField mapHeightTF = new TextField();
        // szerokość mapy
        Label mapWidth = new Label("Map width");
        TextField mapWidthTF = new TextField();
        // liczba zwierząt
        Label animalsInt = new Label("Animals");
        TextField animalsIntTF = new TextField();
        // startowa energia zwierząt
        Label startEnergy = new Label("Animal start energy");
        TextField startEnergyTF = new TextField();
        // startowa liczba roślin
        Label grassInt = new Label("Grasses");
        TextField grassIntTF = new TextField();
        // energia dostarczana przez roślinę
        Label grassEnergy = new Label("Energy provided by grass");
        TextField grassEnergyTF = new TextField();
        // liczba roślina wyrastająca każdego dnia
        Label dailyGrass = new Label("Daily grown grasses");
        TextField dailyGrassTF = new TextField();
        // długość genomu zwierzaków
        Label numberOfGenes = new Label("Genotype length");
        TextField numberOfGenesTF = new TextField();
        // minimalna energia potrzebna do rozmnażania
        Label minCopEnergy = new Label("Minimum energy for copulation");
        TextField minCopEnergyTF = new TextField();
        // energia zużywana by stworzyć potomka
        Label copLostEnergy = new Label("Energy used for copulation");
        TextField copLostEnergyTF = new TextField();
        // minimalna liczba mutacji u potomków
        Label minMutation = new Label("Minimum number of mutations in child's genotype");
        TextField minMutationTF = new TextField();
        // maksymalna liczba mutacji u potomków
        Label maxMutation = new Label("Maximum number of mutations in child's genotype");
        TextField maxMutationTF = new TextField();
        // wariant mapy
        Label mapTypeGlobe = new Label("Map type");
        String[] mapTypes = {"Globe", "Hell"};
        ComboBox comboBoxMapType = new ComboBox(FXCollections.observableArrayList(mapTypes));
        // wariant mutacji
        Label mutationFullyRandom = new Label("Mutation type");
        String[] mutationTypes = {"Full randomness", "Small correction"};
        ComboBox comboBoxMutationType = new ComboBox(FXCollections.observableArrayList(mutationTypes));
        // wariant zachowania
        Label behaviour = new Label("Behaviour");
        String[] behaviourTypes = {"Full predestination", "A bit of madness"};
        ComboBox comboBoxBehaviour = new ComboBox(FXCollections.observableArrayList(behaviourTypes));
        //wariant wzrostu roślin
        Label grassGrowth = new Label("Grass growth type: ");
        String[] grassGrowthType = {"Forested equators", "Toxic corpses"};
        ComboBox comboBoxGrassGrowth = new ComboBox(FXCollections.observableArrayList(grassGrowthType));
        // zapisywanie statystyk symulacji do pliku csv
        CheckBox saveStatisticsCB = new CheckBox("Save statistics");
        saveStatisticsCB.setIndeterminate(false);

        settingsGrid.add(mapHeight,0,1);
        settingsGrid.add(mapHeightTF,1,1);
        settingsGrid.add(mapWidth, 0,2);
        settingsGrid.add(mapWidthTF,1,2);
        settingsGrid.add(animalsInt, 0,3);
        settingsGrid.add(animalsIntTF, 1,3);
        settingsGrid.add(startEnergy, 0,4);
        settingsGrid.add(startEnergyTF, 1,4);
        settingsGrid.add(grassInt, 0,5);
        settingsGrid.add(grassIntTF, 1,5);
        settingsGrid.add(grassEnergy, 0,6);
        settingsGrid.add(grassEnergyTF,1,6);
        settingsGrid.add(dailyGrass, 0, 7);
        settingsGrid.add(dailyGrassTF, 1, 7);
        settingsGrid.add(numberOfGenes, 0,8);
        settingsGrid.add(numberOfGenesTF, 1,8);
        settingsGrid.add(minCopEnergy,0,9);
        settingsGrid.add(minCopEnergyTF, 1,9);
        settingsGrid.add(copLostEnergy, 0,10);
        settingsGrid.add(copLostEnergyTF,1,10);
        settingsGrid.add(minMutation, 0,11);
        settingsGrid.add(minMutationTF, 1,11);
        settingsGrid.add(maxMutation, 0,12);
        settingsGrid.add(maxMutationTF, 1,12);
        settingsGrid.add(mapTypeGlobe, 0,13);
        settingsGrid.add(comboBoxMapType, 1, 13);
        settingsGrid.add(mutationFullyRandom, 0,14);
        settingsGrid.add(comboBoxMutationType, 1,14);
        settingsGrid.add(behaviour, 0, 15);
        settingsGrid.add(comboBoxBehaviour, 1, 15);
        settingsGrid.add(grassGrowth, 0, 16);
        settingsGrid.add(comboBoxGrassGrowth, 1, 16);
        settingsGrid.add(saveStatisticsCB, 1, 17);

        Button startButton = new Button("Start own simulation");
        Button cancelButton = new Button("Close");
        Button useConfigurationButton = new Button("Use prepared configuration");
        HBox hbox = new HBox(20, startButton, cancelButton);
        VBox vBox = new VBox(20, hbox, useConfigurationButton);
        hbox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        startButton.setOnAction(event -> {
            simulationStarted = true;
            try {
                this.mapWidth = Integer.parseInt(mapWidthTF.getText());
                this.mapHeight = Integer.parseInt(mapHeightTF.getText());
                this.animalsInt = Integer.parseInt(animalsIntTF.getText());
                this.startEnergy = Integer.parseInt(startEnergyTF.getText());
                this.grassInt = Integer.parseInt(grassIntTF.getText());
                this.grassEnergy = Integer.parseInt(grassEnergyTF.getText());
                this.dailyGrass = Integer.parseInt(dailyGrassTF.getText());
                this.numberOfGenes = Integer.parseInt(numberOfGenesTF.getText());
                this.minCopEnergy = Integer.parseInt(minCopEnergyTF.getText());
                this.copLostEnergy = Integer.parseInt(copLostEnergyTF.getText());
                this.minMutation = Integer.parseInt(minMutationTF.getText());
                this.maxMutation = Integer.parseInt(maxMutationTF.getText());
                if (comboBoxMapType.getValue() == "Globe") {
                    this.mapTypeGlobe = true;
                } else if (comboBoxMapType.getValue() == "Hell") {
                    this.mapTypeGlobe = false;
                }
                if (comboBoxMutationType.getValue() == "Full randomness") {
                    this.mutationFullyRandom = true;
                } else if (comboBoxMutationType.getValue() == "Small correction") {
                    this.mutationFullyRandom = false;
                }
                if (comboBoxBehaviour.getValue() == "Full predestination") {
                    this.behaviour = true;
                } else if (comboBoxBehaviour.getValue() == "A bit of madness") {
                    this.behaviour = false;
                }
                if (comboBoxGrassGrowth.getValue() == "Forested equators") {
                    this.grassGrowthEquators = true;
                } else if (comboBoxGrassGrowth.getValue() == "Toxic corpses") {
                    this.grassGrowthEquators = false;
                }
                if (saveStatisticsCB.isSelected()) {
                    saveStatistics = true;
                }
            } catch (IllegalArgumentException e) {
                endSimulation("Invalid input. Try again");
                simulationStarted = false;
            }
            this.map = new EvolutionMap(this.mapWidth,this.mapHeight,this.grassInt,this.grassEnergy, this.minCopEnergy, this.copLostEnergy, this.minMutation, this.maxMutation, this.mapTypeGlobe, this.grassGrowthEquators);
            Vector2d[] pos = new Vector2d[0];
            for (int i = 0; i < this.animalsInt; i++) {
                pos = addElement(new Vector2d(generateRandInt(this.mapWidth),generateRandInt(this.mapHeight)), pos);
            }
            engine = new SimulationEngine(this.map, pos, this.dailyGrass, this.startEnergy, this.numberOfGenes, this.mutationFullyRandom, this.behaviour, this);
            startSimulation();
        });

        useConfigurationButton.setOnAction(event -> {
            usePreparedConf();
        });

        cancelButton.setOnAction(event ->
        {
            primaryStage.close();
            System.exit(0);
        });
        VBox vbox = new VBox(settingsGrid, vBox);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Simulation");
        alert.setHeaderText("Information");
        alert.setContentText("Each simulation will end after 350 days");
        alert.showAndWait();

        Scene scene = new Scene(vbox, width, height);
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void usePreparedConf() {
        Button useConf1Button = new Button("Configuration 1");
        Button useConf2Button = new Button("Configuration 2");
        Button useConf3Button = new Button("Configuration 3");
        Button closeButton = new Button("Close");
        VBox vbox = new VBox(10, useConf1Button, useConf2Button, useConf3Button, closeButton);
        vbox.setAlignment(Pos.CENTER);
        Scene newScene = new Scene(vbox, 100,200);
        confWindow = new Stage();
        confWindow.setTitle("Configurations");
        confWindow.setScene(newScene);
        confWindow.show();
        useConf1Button.setOnAction(event -> {
            this.fileName = "src/main/resources/conf1.csv";
            csvReaderSimulation();
        });
        useConf2Button.setOnAction(event -> {
            this.fileName = "src/main/resources/conf2.csv";
            csvReaderSimulation();
        });
        useConf3Button.setOnAction(event -> {
            this.fileName = "src/main/resources/conf3.csv";
            csvReaderSimulation();
        });
        closeButton.setOnAction(event -> confWindow.close());
    }

    private void csvReaderSimulation () {
        CSVReader csvReader = null;
        try {
            simulationStarted = true;
            csvReader = new CSVReader(new FileReader(this.fileName));
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                this.mapWidth = Integer.parseInt(line[0]);
                this.mapHeight = Integer.parseInt(line[1]);
                this.animalsInt = Integer.parseInt(line[2]);
                this.startEnergy = Integer.parseInt(line[3]);
                this.grassInt = Integer.parseInt(line[4]);
                this.grassEnergy = Integer.parseInt(line[5]);
                this.dailyGrass = Integer.parseInt(line[6]);
                this.numberOfGenes = Integer.parseInt(line[7]);
                this.minCopEnergy = Integer.parseInt(line[8]);
                this.copLostEnergy = Integer.parseInt(line[9]);
                this.minMutation = Integer.parseInt(line[10]);
                this.maxMutation = Integer.parseInt(line[11]);
                if (line[12].equals("true")) {
                    this.mapTypeGlobe = true;
                } else if (line[12].equals("false")) {
                    this.mapTypeGlobe = false;
                }
                if (line[13].equals("true")) {
                    this.mutationFullyRandom = true;
                } else if (line[13].equals("false")) {
                    this.mutationFullyRandom = false;
                }
                if (line[14].equals("true")) {
                    this.behaviour = true;
                } else if (line[14].equals("false")) {
                    this.behaviour = false;
                }
                if (line[15].equals("true")) {
                    this.grassGrowthEquators = true;
                } else if (line[15].equals("false")) {
                    this.grassGrowthEquators = false;
                }
                if (line[16].equals("true")) {
                    this.saveStatistics = true;
                } else if (line[16].equals("false")) {
                    this.saveStatistics = false;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            simulationStarted = false;
        }
        this.map = new EvolutionMap(this.mapWidth,this.mapHeight,this.grassInt,this.grassEnergy, this.minCopEnergy, this.copLostEnergy, this.minMutation, this.maxMutation, this.mapTypeGlobe, this.grassGrowthEquators);
        Vector2d[] pos = new Vector2d[0];
        for (int i = 0; i < this.animalsInt; i++) {
            pos = addElement(new Vector2d(generateRandInt(this.mapWidth),generateRandInt(this.mapHeight)), pos);
        }
        engine = new SimulationEngine(this.map, pos, this.dailyGrass, this.startEnergy, this.numberOfGenes,  this.mutationFullyRandom, this.behaviour, this);
        startSimulation();
    }
    private void startSimulation() {
        Button pauseButton = new Button("Pause simulation");
        Button stopButton = new Button("Stop simulation");
        vboxDetails = new VBox(10, pauseButton, stopButton);
        vboxDetails.setAlignment(Pos.TOP_CENTER);
        showStatistics(vboxDetails);

        HBox hBox = new HBox(30, gridPane, vboxDetails);
        Scene secondScene;
        if (this.map.width <= 15 && this.map.height <= 15) {
            secondScene = new Scene(hBox, (this.map.width + 2) * 40 + 600, (this.map.height + 5) * 40);
        } else {
            secondScene = new Scene(hBox, (this.map.width + 2) * 10 + 600, (this.map.height + 5) * 10);
        }

        newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.show();
        Thread thread = new Thread((Runnable) engine);
        thread.start();

        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int pauseClicksCounter = 1;
            @Override
            public void handle(MouseEvent event) {
                if (pauseClicksCounter % 2 == 1) {
                    pauseButton.setText("Resume simulation");
                    engine.pause();
                } else {
                    pauseButton.setText("Pause simulation");
                    engine.resume();
                }
                pauseClicksCounter += 1;
            }
        });
        stopButton.setOnAction(event -> {
            newWindow.close();
            thread.stop();
            endSimulation("You ended simulation");
        });
    }

    private void createGrid() {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i <= map.width + 1; i++) {
            if (this.map.width <= 15 && this.map.height <= 15) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(40));
            } else {
                gridPane.getColumnConstraints().add(new ColumnConstraints(10));
            }
        }
        for (int i = 0; i <= map.height + 1; i++) {
            if (this.map.width <= 15 && this.map.height <= 15) {
                gridPane.getRowConstraints().add(new RowConstraints(40));
            } else {
                gridPane.getRowConstraints().add(new RowConstraints(10));
            }
        }

        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(map.width, map.height);
        // header
        drawHeader(lowerLeft, upperRight);
        // bok
        for (int i = upperRight.y; i >= lowerLeft.y; i--) {
            Label label = new Label(String.format("%d", i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, upperRight.y - i + 1);
            for (int j = lowerLeft.x; j <= upperRight.x; j++) {
                drawObject(new Vector2d(j, i));
            }
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

    private void drawObject(Vector2d currentPosition) {
        if (this.map.isOccupied(currentPosition)) {
            Object object = this.map.objectAt(currentPosition);
            if (object.getClass().equals(Animal.class) || object.getClass().equals(Grass.class)) {
                if (this.map.width <= 15 && this.map.height <= 15) {
                    this.guiElementBox = new GuiElementBox((IMapElement) object, 40, this);
                } else {
                    this.guiElementBox = new GuiElementBox((IMapElement) object, 10, this);
                }

                gridPane.add(guiElementBox.vbox, currentPosition.x + 1, map.height - currentPosition.y + 1);
                GridPane.setHalignment(guiElementBox.vbox, HPos.CENTER);
            } else if (object instanceof ArrayList<?>) {
                for (Animal animal : (ArrayList<Animal>) object) {
                    Platform.runLater(() -> {
                        if (this.map.width <= 15 && this.map.height <= 15) {
                            this.guiElementBox = new GuiElementBox(animal, 40, this);
                        } else {
                            this.guiElementBox = new GuiElementBox(animal, 10, this);
                        }
                        gridPane.add(guiElementBox.vbox, currentPosition.x + 1, map.height - currentPosition.y + 1);
                        GridPane.setHalignment(guiElementBox.vbox, HPos.CENTER);
                    });

                }
            }
        }
    }

    public void updateGrid() {
        Platform.runLater(() -> {
                    gridPane.getChildren().clear();
                    gridPane.setGridLinesVisible(false);
                    createGrid();
                });
    }

    public void updateStatistics() {
        Platform.runLater(() -> {
            showStatistics(vboxDetails);
            if (animalTracking) {
                showAnimalStatistics(vboxDetails);
            }
        });
    }

    public void endSimulation(String info) {
        Platform.runLater(() ->
        {
            newWindow.close();
            Thread.currentThread().interrupt();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simulation ended");
            alert.setHeaderText("The simulation has ended");
            alert.setContentText(info);
            alert.showAndWait();
        });
    }

    private Vector2d[] addElement(Vector2d vector2d, Vector2d[] positions) {
        Vector2d[] newarr= new Vector2d[positions.length + 1];
        for (int i = 0; i < positions.length; i++) {
            newarr[i] = positions[i];
        }
        newarr[positions.length] = vector2d;
        return newarr;
    }

    private int generateRandInt(int max) {
        Random rand = new Random();
        int randInt = rand.nextInt(max + 1);
        return randInt;
    }

    private void showStatistics(VBox vboxWithButtons) {
        VBox vbox = new VBox();
        Label statisticsLabel = new Label("STATISTICS");
        statisticsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
        Text daysText = new Text("Day: " + engine.simulationStatistics.daysCounter);
        Text grassesText = new Text("Grasses: " + engine.simulationStatistics.grassesCounter);
        Text animalsText = new Text("Animals: " + engine.simulationStatistics.animalsCounter);
        Text emptyCellsText = new Text("Empty cells: " + engine.simulationStatistics.emptyCells);
        Text averageEnergyText = new Text("Average animals' energy: " + engine.simulationStatistics.averageEnergy);
        Text averageLifeLength = new Text("Average life length for dead animals: " + engine.simulationStatistics.averageLifeLength);
        vbox.getChildren().addAll(statisticsLabel, daysText, grassesText, animalsText, emptyCellsText, averageEnergyText, averageLifeLength);
        if (engine.simulationStatistics.daysCounter != 0) {
            vboxWithButtons.getChildren().remove(2);
        }
        vboxWithButtons.getChildren().add(2, vbox);
    }

    public void showAnimalStatistics(VBox vboxWithButtons) {
        VBox vBox = new VBox();
        Label animalStatisticsLabel = new Label("ANIMAL STATISTICS");
        animalStatisticsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
        Text genesText = new Text("Genes: " + engine.animalStatistics.genes);
        Text activeGenText = new Text("Active gen: Genes[" + engine.animalStatistics.activeGen + "] = " + engine.animalStatistics.genes.genes_array[engine.animalStatistics.activeGen]);
        Text energyText = new Text("Energy: " + engine.animalStatistics.energy);
        Text eatenGrassesText = new Text("Eaten grasses: " + engine.animalStatistics.eatenGrasses);
        Text childrenCountText = new Text("Children: " + engine.animalStatistics.childrenCount);
        Button stopTrackingAnimal = new Button("Stop tracking animal's statistics");

        vBox.getChildren().addAll(animalStatisticsLabel, genesText, activeGenText, energyText, eatenGrassesText, childrenCountText);
        if (engine.animalStatistics.deathDay != 0) {
            Text deathDayText = new Text("Day of death: " + engine.animalStatistics.deathDay);
            vBox.getChildren().add(deathDayText);
        } else {
            Text lifeLengthText = new Text("Life length: " + engine.animalStatistics.daysAlive);
            vBox.getChildren().add(lifeLengthText);
        }
        vBox.getChildren().add(stopTrackingAnimal);
        stopTrackingAnimal.setOnAction(event -> {
            this.animalTracking = false;
            this.trackedAnimal = null;
            this.engine.animalStatistics = null;
            vboxWithButtons.getChildren().remove(vboxWithButtons.getChildren().size() - 1);
        });
        if (engine.animalStatistics.animalsStatisticsCounter != 1) {
            vboxWithButtons.getChildren().remove(vboxWithButtons.getChildren().size() - 1);
        }
        vboxWithButtons.getChildren().add(vBox);
    }

    public void writeStatistics() {
        String[] header = {"simulationDay", "grasses", "animals", "empty_cells", "average_energy", "average_life_length"};

        String filePath = "src/main/resources/statistics.csv";
        File file = new File(filePath);
        boolean fileExists = file.exists();

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath, true));
            if (!fileExists) {
                writer.writeNext(header);
            }

            String[] data = {
                    String.valueOf(engine.simulationStatistics.daysCounter), String.valueOf(engine.simulationStatistics.grassesCounter),
                    String.valueOf(engine.simulationStatistics.animalsCounter), String.valueOf(engine.simulationStatistics.emptyCells),
                    String.valueOf(engine.simulationStatistics.averageEnergy), String.valueOf(engine.simulationStatistics.averageLifeLength)
            };
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
