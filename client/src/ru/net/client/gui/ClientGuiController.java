package ru.net.client.gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.WindowEvent;
import ru.net.client.Client;
import ru.net.network.Message;

public class ClientGuiController {
    @FXML
    public ListView<HBox> output;
    @FXML
    public TextField input;
    public TextField search;

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
            return;
        }
        input.clear();
        getClient().onSendPackage(client.getConnection(), msg + "\uD83D\uDE00");
    }

    private static boolean filter (String msg) {
        boolean flag = true;
        if (msg.equals("")) flag = false;
        for (int i = 0; i < msg.length(); i++) {
            if(msg.charAt(i) == ' ') flag = false;
        }
        return flag;
    }
    //TODO: посмотреть, как можно упростить метод.

    public void print(Message msg) {
        String text = msg.getStringValue();
        Boolean inOrOut = msg.isInOrOut();
        Platform.runLater(() ->
                output.getItems().add(new HBoxChat(text, inOrOut))
        );
    }
}
