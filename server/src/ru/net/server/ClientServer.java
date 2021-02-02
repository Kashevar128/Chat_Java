package ru.net.server;

import ru.net.network.ListenerNetwork;
import ru.net.network.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ClientServer implements ListenerNetwork {
    int port = 8189;
    ArrayList<TCPConnection> connectionList = new ArrayList<>();

    public static void main(String[] args) {
        new ClientServer();
    }

    public ClientServer() {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                new TCPConnection(serverSocket.accept(), this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void connectionIsReady(TCPConnection tcpConnection) {
        connectionList.add(tcpConnection);
        sendToAllConnections("Клиент подключился: " + tcpConnection);
    }

    @Override
    public void connectionTerminated(TCPConnection tcpConnection) {
        connectionList.remove(tcpConnection);
        sendToAllConnections("Клиент ушел: " + tcpConnection);
    }

    @Override
    public void receivingMessage(TCPConnection tcpConnection, String msg) {
        sendToAllConnections(msg);
    }

    @Override
    public void exception(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exeption: " + e);
    }

    private  void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connectionList.size(); i++) {
            connectionList.get(i).sendMessage(value);
        }
    }
}
