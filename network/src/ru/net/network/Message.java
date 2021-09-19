package ru.net.network;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {

    private String nameUser;
    private String stringValue;

    public Message(String stringValue, String nameUser) {
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getStringValue() {
        return stringValue;
    }
}
