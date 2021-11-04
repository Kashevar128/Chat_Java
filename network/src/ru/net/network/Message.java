package ru.net.network;

import java.io.Serializable;

public class Message implements Serializable {

    private TypeMessage typeMessage;
    private String nameUser;
    private String stringValue;
    private boolean inOrOut;
    private ID id;

    public Message(String stringValue, String nameUser, TypeMessage typeMessage, ID id) {
        this.id = id;
        this.typeMessage = typeMessage;
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
        this.inOrOut = true;
    }

    public ID getId() {
        return id;
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

}
