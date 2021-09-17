package ru.net.client;

import ru.net.client.gui.ClientGui;
import ru.net.client.gui.ClientGuiController;
import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import java.io.IOException;

public class Client implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "172.22.34.61";// 192.168.0.104 - доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом

    private ClientGuiController controller;
    private TCPConnection connection; // Поле для экземпляра канала
    private Client client;

    public Client(ClientGuiController controller) throws IOException {
        this.controller = controller;
        try { // Блок для обхода исключений
            connection = new TCPConnection(this, IP_ADDR, PORT); // Создаем TCP - соединение
        } catch (IOException e) {
            printMsg("Server was broken");
        }
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) { // Расписываем интерфейсы для работы со стороны клиента, методы синхронизировать не надо, т.к. с ними работаем только сам клиент
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
        System.out.println(value);
    }
    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg) {                            // Метод для выведения сообщения в поле диалога
        controller.print(msg);
    }

    public synchronized void send(String msg) {
        connection.sendMessage(msg);
    }


}
