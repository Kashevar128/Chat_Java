package ru.net.client;

import ru.net.client.gui.ClientGui;
import ru.net.client.gui.ClientGuiController;
import ru.net.network.Message;
import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import java.io.IOException;

public class Client implements TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "172.22.34.61";// 192.168.0.104 - доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом

    private ClientGuiController controller;
    private TCPConnection connection; // Поле для экземпляра канала

    public Client(ClientGuiController controller) throws IOException {
        this.controller = controller;
        try { // Блок для обхода исключений
            connection = new TCPConnection(this, IP_ADDR, PORT); // Создаем TCP - соединение
        } catch (IOException e) {
            System.out.println("Сервер не работает");
        }
    }

    public TCPConnection getConnection() {
        return connection;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) { // Расписываем интерфейсы для работы со стороны клиента, методы синхронизировать не надо, т.к. с ними работаем только сам клиент
        System.out.println("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, Message msg) {
        printMsg(msg);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Соединение " + tcpConnection + " закрыто");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Сервер закрыт!");
    }

    private void printMsg(Message msg) { // Метод для выведения сообщения в поле диалога
        controller.print(msg);
    }

    public void send(String msg) {
        Message value = new Message(msg, controller.search.getText());
        connection.sendMessage(value);
    }

}
