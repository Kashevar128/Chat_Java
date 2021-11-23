package gui;

import clientlogic.Client;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.net.network.TCPConnectionListener;

import java.util.EventListener;


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

        client = new Client(controller, nameUser, this);
        controller.setClient(client);

        stage.setOnCloseRequest(event -> {
            System.out.println("Клиент закрыт");
            try {
                client.onDisconnect(client.getConnection());
                client.getConnection().disconnect();
            } catch (Exception e) {
                System.out.println("Корректное завершение работы клиента");
            }
        });
    }

    public String getNameUser() {
        return nameUser;
    }

    public Stage getStage() {
        return stage;
    }
}
