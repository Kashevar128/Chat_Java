package ru.net.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.net.client.Client;

import java.io.IOException;

public class ClientGui extends Application {
    private Stage stage;
    private ClientGuiController controller;
    private Client client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientWindow.fxml"));
        Parent chat = loader.load();
        controller = loader.getController();
        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(true);
        stage.show();
        client = new Client(controller);
        controller.setClient(client);
    }
}
