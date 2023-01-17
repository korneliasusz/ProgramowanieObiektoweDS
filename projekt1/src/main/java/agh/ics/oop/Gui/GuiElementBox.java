package agh.ics.oop.Gui;

import agh.ics.oop.Elements.Animal;
import agh.ics.oop.Interfaces.IMapElement;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public VBox vbox = new VBox();
    public Animal clickedAnimal;

    public GuiElementBox(IMapElement object, int size, App app) {
        try {
            Image image = new Image(new FileInputStream(object.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);

            imageView.setFitHeight(size);
            if (object instanceof Animal) {
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        clickedAnimal = (Animal) object;
                        app.animalTracking = true;
                        app.trackedAnimal = clickedAnimal;
                        Circle circle = new Circle((float)size/2,(float)size/2, (float)size/2);
                        imageView.setClip(circle);
                    }
                });
            }

            vbox.getChildren().add(imageView);
            vbox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
