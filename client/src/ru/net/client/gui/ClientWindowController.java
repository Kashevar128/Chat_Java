package ru.net.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ClientWindowController {
    @FXML
    public ListView<HBox> output;
    @FXML
    public TextField input;

    public void send() {
        if (!filter(input.getText())) {
            input.clear();
            return;
        }
        addToChat(input.getText());
        input.clear();
    }

    public void printFX(String msg) {
        addToChat(msg);
    }

    private static boolean filter (String msg) {
        boolean flag = true;
        if (msg.equals("")) flag = false;
        for (int i = 0; i < msg.length(); i++) {
            if(msg.charAt(i) == ' ') flag = false;
        }
        return flag;
    }

    private void addToChat(String msg) {
        LabelChat labelChat = new LabelChat(msg);
        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource("img/544_oooo.plus.png")));
        ImageView profileImage = new ImageView(image);
        profileImage.setFitHeight(60);
        profileImage.setFitWidth(60);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(profileImage, labelChat);
        HBox.setMargin(profileImage, new Insets(10, 10, 10, 10));
        HBox.setMargin(labelChat, new Insets(20, 10, 20, 10));
        output.getItems().add(hBox);
        int index = output.getSelectionModel().getSelectedIndex();
        output.getSelectionModel().clearSelection(index);
    }

}
