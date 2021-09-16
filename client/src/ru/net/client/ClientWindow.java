package ru.net.client;

import ru.net.client.gui.ChatGui;
import ru.net.client.gui.ClientWindowController;
import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "172.22.34.61";// 192.168.0.104 - доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом
    private ClientWindowController windowController;

    public static void main(String[] args) {
        try {
            new ClientWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TCPConnection connection; // Поле для экземпляра канала

    private ClientWindow() throws IOException {
        new ChatGui();
        windowController = new ClientWindowController();
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
        windowController.printFX(msg);
    }
}
