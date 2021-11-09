package gui;

import clientlogic.Client;
import clientlogic.TestDataBase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ClientGui extends Application {
    private Stage stage;
    private ClientGuiController controller;
    private Client client;
    private String nameUser;

//    public ClientGui(String name) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatWork.fxml"));
//        Parent chat = loader.load();
//        controller = loader.getController();
//        nameUser = name;
//
//        stage = new Stage();
//        stage.setTitle("Сетевой чат");
//        stage.setScene(new Scene(chat));
//        stage.setResizable(false);
//        stage.show();
//
//        client = new Client(controller, nameUser);
//        controller.setClient(client);
//
//
//
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.out.println("Клиент закрыт");
//                try {
//                    client.getConnection().disconnect();
//                } catch (Exception e) {
//                    System.out.println("Корректное завершение работы клиента");
//                }
//            }
//        });
//    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatWork.fxml"));
        Parent chat = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        stage.show();

        nameUser = TestDataBase.getInstance().getName();

        client = new Client(controller, nameUser);
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
}
