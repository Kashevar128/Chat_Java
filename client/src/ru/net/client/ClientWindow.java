package ru.net.client;

import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener { // делаем наследоваие от JFrame и осуществляем интерфейсы ActionListener и TCPConnectionListener

    private static final String IP_ADDR = "192.168.0.104";//172.22.34.61 - доп. IP // Переменная c IP машины
    private static final int PORT = 8189; // Переменная с портом
    private static final int WIDTH = 600; // Переменная с шириной окна
    private static final int HEIGHT = 400; // Переменная с высотой окна

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Доп. поток для работы клиентского окна, нужен для корректной работы Java Swing
            new ClientWindow(); // Инстанс окна пользователя
        });
    }

    private final JTextArea textArea = new JTextArea(); // Создаем поле, которое будет отражать диалоги
    private final JTextField fieldNickname = new JTextField("alex"); // Поле с ником пользователя
    private final JTextField fieldInput = new JTextField(); // Поле для ввода сообщений

    private TCPConnection connection; // Поле для экземпляра канала

    private ClientWindow() {                                      // Настройки клиентского окна
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
       setVisible(true); //Пишем - показать окно

        try { // Блок для обхода исключений
            connection = new TCPConnection(this, IP_ADDR, PORT); // Создаем TCP - соединение
        } catch (IOException e) {
            this.onException(connection, e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Переопределнный метод, реагирующий на событие - нажатие enter
        String msg = fieldInput.getText(); // Записываем в переменную текст из поля ввода сообщения
        if(msg.equals("")) return; // Если переменная равна пустому месту, делаем возврат из метода
        fieldInput.setText(null); // Передаем null в поле ввода сообщения, чтобы очистить его
        connection.sendMessage(fieldNickname.getText() + ": " + msg); // отправляем его с помощью метода TCPConnection
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
        SwingUtilities.invokeLater(() -> {                                      // В отдельном потоке, т.к. так как есть возможность одновременного обращения к методу из нескольких мест в программе
            textArea.append(msg + "\n");                                        // Добавление сообщения
            textArea.setCaretPosition(textArea.getDocument().getLength());      // Переведение каретки в конец строки после сообщения, чтобы иметь пустую строку между сообщениями.
        });
    }
}
