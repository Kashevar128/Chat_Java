package gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;
import ru.net.network.ClientProfile;
import ru.net.network.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HBoxChat extends HBox {

    private ImageView photo;
    private Label label;
    private String userName;
    private String stringValue;
    private boolean inOrOut;

    public HBoxChat(Message message) {
        this.userName = message.getProfile().getNameUser();
        this.stringValue = message.getStringValue();
        this.inOrOut = message.isInOrOut();
        this.photo = decode(message.getProfile().getAvatar());
        this.label = getDialogLabel(inOrOut, stringValue);

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

    public HBoxChat(ClientProfile clientProfile) {
        this.photo = decode(clientProfile.getAvatar());
        this.userName = clientProfile.getNameUser();
        this.label = getListUsersLabel(userName);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(photo, label);
    }

    Label getListUsersLabel(String name) {
        label = new Label(name);
        label.setFont(new Font("Arial", 16));
        label.setMaxWidth(200);
        label.setWrapText(true);

        return label;
    }

    Label getDialogLabel(boolean inOrOut, String text) {
        String stylesInGoingLabel = "-fx-background-color: #D3EEDF;" +
                "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

        String stylesOutGoingLabel = "-fx-background-color: #AAE0FF;" + //
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

    private ImageView decode(byte[] byteAva) {
        ImageView ava = new ImageView();
        ByteArrayInputStream bais = new ByteArrayInputStream(byteAva);
        try {
            BufferedImage image = ImageIO.read(bais);
            Image imageFX = SwingFXUtils.toFXImage(image, null);
            ava.setImage(imageFX);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ava;
    }

//    ImageView getPhoto(String userName) {
//        StringBuilder value = new StringBuilder();
//        value.append("client\\src\\main\\resources\\img\\");
//        value.append(userName);
//        value.append(".png");
//        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource(value.toString())));
//        ImageView profileImage = new ImageView(image);
//        profileImage.setFitHeight(60);
//        profileImage.setFitWidth(60);
//        return profileImage;
//    }
}


