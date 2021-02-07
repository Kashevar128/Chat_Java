package ru.net.server;

import ru.net.network.AuthService;
import ru.net.network.BaseAuthService;
import ru.net.network.ListenerNetwork;
import ru.net.network.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientServer implements ListenerNetwork, Runnable {

    Thread t;
    int port = 8189;
    private AuthService authService;
    ArrayList<TCPConnection> connectionList = new ArrayList<>();

    public static void main(String[] args) {
        new ClientServer();
    }

    public ClientServer() {
        System.out.println("Сервер запущен...");
        authService = new BaseAuthService();
        t = new Thread(this);
        t.start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                new TCPConnection(this ,serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void connectionIsReady(TCPConnection tcpConnection, String nick) {
        sendToAllConnections(nick + " зашел в чат");
        connectionList.add(tcpConnection);
    }

    @Override
    public synchronized void connectionTerminated(TCPConnection tcpConnection, String nick) {
        sendToAllConnections(nick + " ушел");
        connectionList.remove(tcpConnection);
    }

    @Override
    public synchronized void receivingMessage(TCPConnection tcpConnection, String msg) {
        if(msg.charAt(0) == '/')authentication(tcpConnection, msg);
        else sendToAllConnections(msg);
    }

    @Override
    public synchronized void exception(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exeption: " + e);
    }

    @Override
    public synchronized void authentication(TCPConnection tcpConnection, String msg) {
        authService.start();
        while (true) {
            if (msg.startsWith("/auth")) {
                String[] parts = msg.split("\\s");
                String nick = authService.getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!isNickBusy(nick)) {
                        tcpConnection.sendMessage("/authok " + nick);
                        tcpConnection.setName(nick);
                        connectionIsReady(tcpConnection, nick);
                        authService.stop();
                        return;
                    } else tcpConnection.sendMessage("Учетная запись уже используется");
                } else tcpConnection.sendMessage("Неверные логин/пароль");
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (TCPConnection o : connectionList) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    private void writeServer() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String msg = scanner.nextLine();
            sendToAllConnections("Сервер: " + msg);
        }
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connectionList.size(); i++) {
            connectionList.get(i).sendMessage(value);
        }
    }

    @Override
    public void run() {
        writeServer();
    }

}
