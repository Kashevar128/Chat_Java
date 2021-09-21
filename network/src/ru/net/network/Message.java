package ru.net.network;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {

    private String nameUser;
    private String stringValue;
    private String IP;
    private int PORT;

    public Message(String stringValue, String nameUser, TCPConnection tcpConnection) {
        this.nameUser = nameUser;
        this.stringValue = "[" + nameUser + "] " + stringValue;
        this.IP = tcpConnection.getSocket().getInetAddress().toString();
        this.PORT = tcpConnection.getSocket().getPort();
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
