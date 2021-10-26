package gui;

import clientlogic.DataBase;
import clientlogic.DataBaseAuthService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {

    public TextField login;
    public TextField password;

    public void enter() throws IOException, SQLException {
        DataBaseAuthService.getInstance().addUser(login.getText(), password.getText());
//        Parent chat = FXMLLoader.load(getClass().getResource("/chatWork.fxml"));
//        Stage stage = new Stage();
//        stage.setTitle("Сетевой чат");
//        stage.setScene(new Scene(chat));
//        stage.setResizable(false);
//        stage.show();
        login.getScene().getWindow().hide();
        DataBase.resultSet();
    }
}
