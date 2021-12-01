package ru.net.server;

import ru.net.network.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import static ru.net.network.TypeMessage.*;

public class ChatServer extends JFrame implements TCPConnectionListener, ActionListener { //Создаем класс ChatServer реализуем интерфейс для переопределения его методов

    public static void main(String[] args) {
        new ChatServer();
    } //Создание нового объекта класса ChatServer

    private static final int WIDTH = 600; // Переменная с шириной окна
    private static final int HEIGHT = 400; // Переменная с высотой окна
    private final ArrayList<TCPConnection> connections = new ArrayList<>(); // Создание коллекцию для создающихся TCP - соединений
    private final ArrayList<ClientProfile> usersProfiles = new ArrayList<>();
    private final JTextArea textArea = new JTextArea(); // Создаем поле, которое будет отражать диалоги
    private final JTextField fieldNickname = new JTextField("Admin"); // Поле с ником пользователя
    private final JTextField fieldInput = new JTextField(); // Поле для ввода сообщений
    private TCPConnection connection = null;
    private static final String NAME_SERVER = "Admin";
    private ClientProfile serverProfile;

    private ChatServer() { // Конструктор класса
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Функция для закрытия окна при нажатии на крестик

        setSize(WIDTH, HEIGHT); // Введение размеров окна
        setLocationRelativeTo(null); // Расположение окна по середине экрана
        setAlwaysOnTop(true); // Расположение в верхней части экрана
        textArea.setEditable(false); // Запрет на редактирование диалогового окна
        textArea.setLineWrap(true); // Автоматический перенос строк
        fieldInput.addActionListener(this); // Добавить на поле событие при нажатии Enter
        add(textArea, BorderLayout.CENTER); // Добавляем диалоговое поле на окно клиента (с типом размещения BorderLayout) по центру
        add(fieldInput, BorderLayout.SOUTH); // Добавляем поле ввода сообщений на юг окна клиента
        add(fieldNickname, BorderLayout.NORTH); // Добавляем поле никнейма на север окна клиента
        setResizable(false);

        setVisible(true); //Пишем - показать окно

        this.serverProfile = new ClientProfile(NAME_SERVER, null);

        printMsg("Server running..."); // Консоль - запуск сервера
        printMsg("You have to wait connection");
        try (ServerSocket serverSocket = new ServerSocket(8189)) { // Создание сервер - сокета на порте 8189, слушающего клиента в try с ресурсами
            while (true) {
                connection = new TCPConnection(serverSocket.accept(), this); // В переменную connection закидываем инстанс
            }
        } catch (IOException e) {
            printMsg("Client kicked");
        }

    }



    private void sendToAllConnections(Message msg) { // Метод для рассылки сообщений всем соединениям сразу
        for (TCPConnection tcpConnection : connections) {
            if (msg.getTypeMessage().equals(VERBAL_MESSAGE)) {
                if (msg.getProfile().equals(tcpConnection.getClientProfile())) {
                    msg.setInOrOut(false);
                } else msg.setInOrOut(true);
            }
            tcpConnection.sendMessage(msg); // Вызываем для каждого метод отправки сообщения класса TCPConnection
        }
    }

    private synchronized void printMsg(String msg) {                            // Метод для выведения сообщения в поле диалога
        SwingUtilities.invokeLater(() -> {                                      // В отдельном потоке, т.к. так как есть возможность одновременного обращения к методу из нескольких мест в программе
            textArea.append(msg + "\n");                                        // Добавление сообщения
            textArea.setCaretPosition(textArea.getDocument().getLength());      // Переведение каретки в конец строки после сообщения, чтобы иметь пустую строку между сообщениями.
        });
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) { //Синхронизируем все методы tcpConnection, т.к. одними и теми же методами могут пользоваться несколько потоков
        connections.add(tcpConnection); // Добавляем в коллекцию соединений новое соединение
        printMsg("Client connected: " + tcpConnection); // Делаем рассылку все подключенным пользователям о создании нового соединения с клиентом
        printMsg(connections.toString());
    }

    @Override
    public synchronized void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        messageHandler(msg, msg.getTypeMessage());
        System.out.println(tcpConnection.getClientProfile().getNameUser());
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection); // Удаление соединения из коллекции соединений
        usersProfiles.remove(tcpConnection.getClientProfile());
        Message messageUpdateList1 = new Message(usersProfiles, null, SERVICE_MESSAGE_UPDATE_LIST_USERS);
        if(!connections.isEmpty()) {
            sendToAllConnections(messageUpdateList1);
        }
        printMsg(usersProfiles.toString());
        printMsg("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("TCPConnection exception: " + e); // Исключение для соединения
    }

    @Override
    public void onSendPackage(TCPConnection tcpConnection, String stringMsg) {
        if (stringMsg.equals("")) return; // Если переменная равна пустому месту, делаем возврат из метода
        fieldInput.setText(null); // Передаем null в поле ввода сообщения, чтобы очистить его
        printMsg(stringMsg);
        Message pack = new Message(stringMsg, serverProfile);
        sendToAllConnections(pack); // Рассылка сообщений клиентам
    }

    @Override
    public void messageHandler(Message msg, TypeMessage typeMessage) {
        switch (typeMessage) {
            case VERBAL_MESSAGE:
                sendToAllConnections(msg);
                printMsg(msg.getStringValue());
                break;
            case SERVICE_MESSAGE_AUTHORIZATION:
                ClientProfile clientProfile = (ClientProfile) msg.getObjT();
                connection.setClientProfile(clientProfile);
                usersProfiles.add(connection.getClientProfile());
                printMsg(usersProfiles.toString());
                Message messageUpdateList = new Message(usersProfiles, null, SERVICE_MESSAGE_UPDATE_LIST_USERS);
                sendToAllConnections(messageUpdateList);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onSendPackage(connection, fieldInput.getText());
    }



}
