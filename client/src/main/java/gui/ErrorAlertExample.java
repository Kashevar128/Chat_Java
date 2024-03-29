package gui;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import java.io.IOException;
import java.util.EventListener;
import java.util.Optional;

public class ErrorAlertExample {


    public static void getErrorConnection() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка подключения.");
        alert.setHeaderText("Нет связи с сервером");
        alert.setContentText("Нажмите ОК для продолжения.");

        alert.showAndWait();
    }

    public static void getErrorConnectionDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Переподключение.");
        alert.setHeaderText("Попробовать переподключиться?");
        alert.setContentText("Нажмите ОК для переподключения или ОТМЕНА - для выходы из диалога.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return;
        }
        getErrorConnectionFatal();
        System.exit(0);
    }

    public static void getErrorConnectionFatal() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка подключения.");
        alert.setHeaderText("Не удалось установить связь");
        alert.setContentText("Нажмите ОК для выхода из приложения.");

        alert.showAndWait();

    }

}

