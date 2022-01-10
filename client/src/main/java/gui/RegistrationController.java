package gui;

import clientlogic.DataBase;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationController {

    public TextField login;
    public TextField password;
    private String strLogin;
    private String strPassword;

    public void enter() throws Exception {
        setStrLogin(login.getText());
        setStrPassword(password.getText());

        boolean filter01 = ClientGuiController.filter(getStrLogin());
        boolean filter02 = ClientGuiController.filter(getStrPassword());
        if (!filter01 || !filter02) {
            login.clear();
            password.clear();
            return;
        }

        if(!strLength(getStrLogin()) || !strLength(getStrPassword())) {
            login.clear();
            password.clear();
            WarningAlertExample.getWarningBigLenght();
            return;
        }

        boolean reg = DataBase.getInstance().addUser(login.getText(), password.getText());
        if (reg) {
            login.getScene().getWindow().hide();
            new ClientGui(login.getText(), DataBase.getAvatar(login.getText()));
            DataBase.resultSet();
        }
    }

    public static boolean strLength(String msg) {
        return msg.length() <= 30;
    }

    public String getStrLogin() {
        return strLogin;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrLogin(String strLogin) {
        this.strLogin = StringUtils.strip(strLogin);
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = StringUtils.strip(strPassword);
    }
}
