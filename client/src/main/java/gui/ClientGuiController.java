package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import clientlogic.Client;


import ru.net.network.Message;
import ru.net.network.TCPConnection;
import ru.net.network.TypeMessage;

import java.util.ArrayList;

public class ClientGuiController {
    @FXML
    public ListView<HBox> output;
    public TextArea input;
    public TextField search;
    public Label name;
    public ListView listUsers;

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

    public void print(TCPConnection connection) {
        String name =  connection.getName();
        Platform.runLater(() ->
                listUsers.getItems().add(new HBoxChat(name))
        );
    }
}
