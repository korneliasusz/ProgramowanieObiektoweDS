package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public VBox vbox = new VBox();

    public GuiElementBox(IMapElement object, int size) {
        try {
            Image image = new Image(new FileInputStream(object.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);
            if (object instanceof Animal) {
                if (size >= 20) {
                    imageView.setFitHeight(size - 15);
                } else {
                    imageView.setFitHeight(size - 7);
                }
                vbox.getChildren().add(new Label(object.toString()));
            } else {
                imageView.setFitHeight(size);
            }
            vbox.getChildren().add(imageView);
            vbox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
