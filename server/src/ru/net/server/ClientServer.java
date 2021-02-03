package ru.net.server;

import ru.net.network.ListenerNetwork;
import ru.net.network.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientServer implements ListenerNetwork, Runnable {

    Thread t;
    int port = 8189;
    ArrayList<TCPConnection> connectionList = new ArrayList<>();

    public static void main(String[] args) {
        new ClientServer();
    }

    public ClientServer() {
        System.out.println("Server running...");
        t = new Thread(this);
        t.start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                new TCPConnection(serverSocket.accept(), this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void connectionIsReady(TCPConnection tcpConnection) {
        connectionList.add(tcpConnection);
        sendToAllConnections("Клиент подключился: " + tcpConnection);
    }

    @Override
    public synchronized void connectionTerminated(TCPConnection tcpConnection) {
        connectionList.remove(tcpConnection);
        sendToAllConnections("Клиент ушел: " + tcpConnection);
    }

    @Override
    public synchronized void receivingMessage(TCPConnection tcpConnection, String msg) {
        sendToAllConnections(msg);
    }

    @Override
    public synchronized void exception(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exeption: " + e);
    }

    private void writeServer() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String msg = "Сообщение всем: " + scanner.nextLine();
            if (msg.charAt(0) == '/') {
                String name = "Имя получателя: " + scanner.nextLine();
                msg = "Сообщение " + name + ": " + scanner.nextLine();
                sendDefinedConnection("Сервер: " + msg, name);
            }
            sendToAllConnections("Сервер: " + msg);
        }
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connectionList.size(); i++) {
            connectionList.get(i).sendMessage(value);
        }
    }

    private void sendDefinedConnection(String value, String name) {
        System.out.println(value);
        boolean flag = false;
        for (int i = 0; i < connectionList.size(); i++) {
            if(connectionList.get(i).getName().equals(name)) {
                connectionList.get(i).sendMessage(value);
                flag = true;
            }
        }
        if (!flag) System.out.println("Участника с таким именем не существует");
    }

    @Override
    public void run() {
        writeServer();
    }
}
