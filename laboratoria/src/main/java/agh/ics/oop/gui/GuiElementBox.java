package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    public VBox vBox = new VBox();

    public GuiElementBox(IMapElement object) {
        try {
            Image image = new Image(new FileInputStream(object.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(new Label(object.getElementName()));
            vBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
