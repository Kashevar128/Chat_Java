package gui;

import clientlogic.DataBase;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationController {

    public TextField login;
    public TextField password;

    public void enter() throws Exception {
            boolean reg = DataBase.getInstance().addUser(login.getText(), password.getText());
            if(reg) {
                login.getScene().getWindow().hide();
                new ClientGui(login.getText());
                DataBase.resultSet();
            }
    }
}
