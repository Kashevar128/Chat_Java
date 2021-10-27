package gui;

import clientlogic.DataBaseAuthService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AuthController {

    public TextField login;
    public TextField password;

    public void enter() throws Exception {
        boolean auth = DataBaseAuthService.getInstance()
                .auth(login.getText(), password.getText());
        if(auth) {
            login.getScene().getWindow().hide();
            new ClientGui();
        }else {
            login.setText("WRONG LOGIN OR PASSWORD");
            password.clear();
        }
    }

    public void reg() throws Exception {
        login.getScene().getWindow().hide();
        new RegGui();
    }
}
