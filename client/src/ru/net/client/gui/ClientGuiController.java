package ru.net.client.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ru.net.client.Client;

public class ClientGuiController {
    @FXML
    public ListView<HBox> output;
    @FXML
    public TextField input;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void msgProcessing() {
        String msg = input.getText();
        if (!filter(msg)) {
            input.clear();
            return;
        }
        getClient().send(msg);
    }

    private static boolean filter (String msg) {
        boolean flag = true;
        if (msg.equals("")) flag = false;
        for (int i = 0; i < msg.length(); i++) {
            if(msg.charAt(i) == ' ') flag = false;
        }
        return flag;
    }

    public void print(String msg) {
        LabelChat labelChat = new LabelChat(msg);
        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource("img/544_oooo.plus.png")));
        ImageView profileImage = new ImageView(image);
        profileImage.setFitHeight(60);
        profileImage.setFitWidth(60);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(profileImage, labelChat);
        HBox.setMargin(profileImage, new Insets(10, 10, 10, 10));
        HBox.setMargin(labelChat, new Insets(20, 10, 20, 10));
        Platform.runLater(() -> output.getItems().add(hBox));
    }

}