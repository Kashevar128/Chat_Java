package gui;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class InformationAlertExample {

    public void getInformationConnectionComplete() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Попытка соединения.");
        alert.setHeaderText("Связь с сервером установлена");
        alert.setContentText("Нажмите ОК для продолжения.");

        alert.showAndWait();
    }

}
