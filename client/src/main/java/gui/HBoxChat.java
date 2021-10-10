package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

public class HBoxChat extends HBox {

    private ImageView photo;
    private Label label;

    public HBoxChat(String text, boolean inOrOut) {
        photo = getPhoto(true);
        label = getLabel(inOrOut, text);
        if(inOrOut) {
            this.setAlignment(Pos.CENTER_LEFT);
             this.getChildren().addAll(photo, label);
        }
        if (!inOrOut) {
            this.setAlignment(Pos.CENTER_RIGHT);
            this.getChildren().addAll(label, photo);
        }

        HBox.setMargin(photo,new Insets(10,10,10,10));
        HBox.setMargin(label,new Insets(20,10,20,10));


    }


    Label getLabel(boolean inOrOut, String text) {
        String stylesOutGoingLabel = "-fx-background-color: #AAE0FF;" + //
                "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

        String stylesInGoingLabel = "-fx-background-color: #D3EEDF;" +
                "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";
        Label label = new Label(text);
        if (inOrOut) {
            label.setStyle(stylesInGoingLabel);
        }
        if (!inOrOut) label.setStyle(stylesOutGoingLabel);
        label.setFont(new Font("Arial", 16));
        label.setMaxWidth(450);
        label.setWrapText(true);

        return label;
    }

    ImageView getPhoto(boolean inOrOut) {
        String image01 = "img/544_oooo.plus.png";
        String image02 = "img/1223_oooo.plus.png";
        String value = null;
        if (inOrOut) value = image01;
        if (!inOrOut) value = image02;

        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource(value)));
        ImageView profileImage = new ImageView(image);
        profileImage.setFitHeight(60);
        profileImage.setFitWidth(60);
        return profileImage;
    }
}


