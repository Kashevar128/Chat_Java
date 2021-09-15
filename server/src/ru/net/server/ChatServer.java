package ru.net.server;

import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener { //Создаем класс ChatServer реализуем интерфейс для переопределения его методов

    public static void main(String[] args) {
        new ChatServer();
    } //Создание нового объекта класса ChatServer

    private final ArrayList<TCPConnection> connections = new ArrayList<>(); // Создание коллекцию для создающихся TCP - соединений
    private TCPConnection connection; // Переменная для новых TCP - соединений

    private ChatServer() { // Конструктор класса
        System.out.println("Server running..."); // Консоль - запуск сервера
        try (ServerSocket serverSocket = new ServerSocket(8189)) { // Создание сервер - сокета на порте 8189, слушающего клиента в try с ресурсами
            while (true) {
                connection = new TCPConnection(serverSocket.accept(), this); // В переменную connection закидываем инстанс
            }
        } catch (IOException e) {
            this.onException(connection, e);
        }
    }

    private void sendToAllConnections(String value) { // Метод для рассылки сообщений всем соединениям сразу
        System.out.println(value);
        for (TCPConnection tcpConnection : connections) { // Проходимся переменной по всей коллекции
            tcpConnection.sendMessage(value); // Вызываем для каждого метод отправки сообщения класса TCPConnection
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) { //Синхронизируем все методы tcpConnection, т.к. одними и теми же методами могут пользоваться несколько потоков
        connections.add(tcpConnection); // Добавляем в коллекцию соединений новое соединение
        sendToAllConnections("Client connected: " + tcpConnection); // Делаем рассылку все подключенным пользователям о создании нового соединения с клиентом
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value); //Метод рассылки всем пользователям
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection); // Удаление соедиения из коллекции соединений
        sendToAllConnections("Client disconnected: " + tcpConnection); // Разослать всем клиентам весть об утраченном соединении
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e); // Исключение для соединения
    }
}
