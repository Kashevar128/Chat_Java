package ru.net.network;

public interface TCPConnectionListener {

    void onConnectionReady (TCPConnection tcpConnection);
    void onReceiveString (TCPConnection tcpConnection, Message msg);
    void onDisconnect (TCPConnection tcpConnection);
    void onException (TCPConnection tcpConnection, Exception e);

}
