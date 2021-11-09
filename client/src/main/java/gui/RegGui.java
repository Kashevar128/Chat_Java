package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegGui extends Application {
    private Stage stage;
    RegistrationController controller;

    public RegGui() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
        Parent reg = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
        Parent reg = loader.load();
        controller = loader.getController();

        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
        stage.show();
    }
}
