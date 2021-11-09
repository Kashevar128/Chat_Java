package gui;

import clientlogic.DataBase;
import javafx.scene.control.TextField;

public class AuthController {

    public TextField login;
    public TextField password;

    public void enter() throws Exception {
        boolean auth = DataBase.getInstance()
                .auth(login.getText(), password.getText());
        if (auth) {
            login.getScene().getWindow().hide();
            new ClientGui(login.getText());
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
