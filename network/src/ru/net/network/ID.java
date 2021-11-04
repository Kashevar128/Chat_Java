package ru.net.network;

import java.io.Serializable;
import java.security.PublicKey;

public class ID implements Serializable {
    private String IP_user;
    private int port_user;

    public ID(String IP_user, int port_user) {
        this.IP_user = IP_user;
        this.port_user = port_user;
    }

    public String getIP_user() {
        return IP_user;
    }

    public int getPort_user() {
        return port_user;
    }
}
