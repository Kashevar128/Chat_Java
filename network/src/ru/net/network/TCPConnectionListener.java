package ru.net.network;

public interface TCPConnectionListener {

    void onConnectionReady (TCPConnection tcpConnection);
    void onReceivePackage(TCPConnection tcpConnection, Message msg);
    void onDisconnect (TCPConnection tcpConnection);
    void onException (TCPConnection tcpConnection, Exception e);
    void onSendPackage (TCPConnection tcpConnection, String msg);
    void messageHandler(Message msg, TypeMessage typeMessage);

}
