package ru.net.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.net.client.Client;

import javax.swing.*;
import java.io.IOException;

public class ClientGui extends Application {
    private Stage stage;
    private ClientGuiController controller;
    private Client client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatWork.fxml"));
        Parent chat = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(true);
        stage.setResizable(false);
        stage.show();

        client = new Client(controller);
        controller.setClient(client);



        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Клиент закрыт");
                try {
                    client.getConnection().disconnect();
                } catch (Exception e) {
                    System.out.println("Корректное завершение работы клиента");
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientGui.launch();
    }
}
