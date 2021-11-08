package ru.net.network;

import java.io.Serializable;

public class Message<T> implements Serializable {

    private TypeMessage typeMessage;
    private String nameUser;
    private String stringValue;
    private boolean inOrOut;
    private ID id;
    private T obj;

    public Message(String stringValue, String nameUser, ID id) {
        this.typeMessage = TypeMessage.VERBAL_MESSAGE;
        this.id = id;
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
        this.inOrOut = true;
    }

    public Message(T obj, TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
        this.obj = obj;
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

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public T getObj() {
        return obj;
    }
}
