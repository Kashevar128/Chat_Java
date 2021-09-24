package ru.net.network;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {

    private String nameUser;
    private String stringValue;
    private String IP;
    private int PORT;
    private boolean inOrOut;

    public Message(String stringValue, String nameUser, String IP, int PORT) {
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
        this.IP = IP;
        this.PORT = PORT;
        this.inOrOut = true;
    }

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getStringValue() {
        return stringValue;
    }

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }
}
