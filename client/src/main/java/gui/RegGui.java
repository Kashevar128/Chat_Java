package gui;

import clientlogic.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegGui extends Application {
    private Stage stage;
    RegistrationController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration.fxml"));
        Parent reg = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
        stage.show();
    }
}
