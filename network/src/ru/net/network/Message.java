package ru.net.network;

import java.io.Serializable;

public class Message<T, V> implements Serializable {

    private TypeMessage typeMessage;
    private ClientProfile profile;
    private String stringValue;
    private boolean inOrOut;
    private T obj;
    private V obj_2;

    public Message(String stringValue, ClientProfile user) {
        this.typeMessage = TypeMessage.VERBAL_MESSAGE;
        this.profile = user;
        this.stringValue = "[" + profile.getNameUser() + "] " + stringValue;
        this.inOrOut = true;
    }

    public Message(T obj, V obj_2, TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
        this.obj = obj;
        this.obj_2 = obj_2;
    }

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    public ClientProfile getProfile() {
        return profile;
    }

    public String getStringValue() {
        return stringValue;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public T getObjT() {
        return obj;
    }
}
