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

    public void enter() throws Exception {
        DataBaseAuthService.getInstance().addUser(login.getText(), password.getText());
        login.getScene().getWindow().hide();
        new ClientGui();
        DataBase.resultSet();
    }
}
