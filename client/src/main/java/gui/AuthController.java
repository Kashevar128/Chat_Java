package gui;

import clientlogic.DataBase;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

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
            login.clear();
            password.clear();
            WarningAlertExample.getWarningLoginOrPasswordFalse();
        }
    }

    public void reg() throws Exception {
        login.getScene().getWindow().hide();
        new RegGui();
    }
}
