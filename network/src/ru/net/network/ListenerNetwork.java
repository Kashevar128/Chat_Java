package ru.net.network;

import java.io.IOException;

public interface ListenerNetwork {

    public void connectionIsReady(TCPConnection tcpConnection);
    public void connectionTerminated(TCPConnection tcpConnection);
    public void receivingMessage(TCPConnection tcpConnection, String msg);
    public void exception(TCPConnection tcpConnection, IOException e);
}
