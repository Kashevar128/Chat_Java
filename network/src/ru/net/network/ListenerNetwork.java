package ru.net.network;

import java.io.IOException;

public interface ListenerNetwork {

    public void connectionIsReady(TCPConnection tcpConnection, String name);
    public void connectionTerminated(TCPConnection tcpConnection, String name);
    public void receivingMessage(TCPConnection tcpConnection, String msg);
    public void exception(TCPConnection tcpConnection, IOException e);
    public void authentication (TCPConnection tcpConnection, String msg);
}
