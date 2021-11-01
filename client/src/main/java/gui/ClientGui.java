package gui;

import clientlogic.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ClientGui {
    private Stage stage;
    private ClientGuiController controller;
    private Client client;

    public ClientGui(String login) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatWork.fxml"));
        Parent chat = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        stage.show();

        client = new Client(controller, login);
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
