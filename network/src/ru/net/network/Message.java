package ru.net.network;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {

    private TypeMessage typeMessage;
    private String nameUser;
    private String stringValue;
    private boolean inOrOut;

    public Message(String stringValue, String nameUser, TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
        this.inOrOut = true;
    }

   // public Message()

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

}
