package gui;

import clientlogic.Client;
import javafx.application.Platform;
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
    private String nameUser;

    public ClientGui(String name) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chatWork.fxml"));
        Parent chat = loader.load();
        controller = loader.getController();
        nameUser = name;

        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        controller.input.setWrapText(true);
        stage.show();

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

    public String getNameUser() {
        return nameUser;
    }
}
