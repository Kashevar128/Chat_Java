package ru.net.client.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatGui {
    private Stage stage;

    public ChatGui() throws IOException {
        Parent chat = FXMLLoader.load(getClass().getResource("/ClientWindow.fxml"));
        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(true);
        stage.show();
    }
}
