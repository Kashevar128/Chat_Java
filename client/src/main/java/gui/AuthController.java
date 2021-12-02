package gui;

import clientlogic.DataBase;
import javafx.application.Platform;
import javafx.scene.control.TextField;

public class AuthController {

    public TextField login;
    public TextField password;

    public void enter() throws Exception {
        boolean auth = DataBase.getInstance()
                .auth(login.getText(), password.getText());
        if (auth) {
            login.getScene().getWindow().hide();
            Platform.runLater(() -> {
                try {
                    new ClientGui(login.getText(), DataBase.getAvatar(login.getText()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            login.setText("WRONG LOGIN OR PASSWORD");
            password.clear();
        }
    }

    public void reg() throws Exception {
        login.getScene().getWindow().hide();
        new RegGui();
    }
}
