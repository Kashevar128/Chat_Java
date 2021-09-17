package ru.net.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPConnection {   // Класс, инкапсулирующий в себе логику интернет - соединения

    private final Socket socket; // Поле сокета
    private Thread readThread; // Поток чтения сообщений, не финал, так как состояние потока может изменится - он может оказаться прерванным
    private final TCPConnectionListener eventListener; // Экземпляр интерфейса для доступа к его методам - событиям
    private final BufferedReader in; // Экземпляр входящего потока в обертке класса для буферизации сообщений
    private final BufferedWriter out; // Экземпляр для исходящего потока в обертке класса для буферизации сообщений

    public TCPConnection (TCPConnectionListener eventListener, String ipAdd, int port) throws IOException {
        this (new Socket(ipAdd, port), eventListener); // Создаем конструктор для нового сокета, использует ссылку на второй конструктор
    }

    public TCPConnection(Socket socket, TCPConnectionListener eventListener) throws IOException { //Конструктор с исключением для случая неудачного установления или прерывания канала чтения, конструктор используется для созданного заранее сокета
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); //Поток, извлекаемый из сокета вместе с типом кодировки передается новому объекту InputStreamReader для конвертирования байтов в символы,
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));//затем все это на вход BufferedReader для удобства чтения и повышения эффективности передачи сообщений за счет наличия промежуточного буфера.
        this.eventListener = eventListener;
        readThread = new Thread( () -> { // На вход экземпляру потока подается анонимный класс имплементированный от Runnable, тут же создается объект
            try {                         //этого класса и пишется тело класса - переопределенный метода run, это все можно заменить объектом с содержанием метода - лямбда - выражением
                eventListener.onConnectionReady(TCPConnection.this); //Событие - установление связи, передаем экземпляр сообщения
                while (!readThread.isInterrupted()) { // Цикл работает, пока не прервется поток чтения
                    String msg = in.readLine(); // Читаем из обработанного потока сообщение в переменную msg
                    eventListener.onReceiveString(TCPConnection.this, msg); // Вызываем событие - получение сообщения, передаем экземпляр подключения и само сообщение
                }
            } catch (IOException e) {
                eventListener.onException(TCPConnection.this, e); // Вызываем событие - выброс исключения
            } finally {
                eventListener.onDisconnect(TCPConnection.this); // Вызываем событие - прерывание связи, но не метод disconnect, так как прерывание со стороны сервера и со стороны клиента будут обрабатываться по разному
            }
        });
        readThread.start(); // Стартуем поток
    }

    public synchronized void sendMessage (String value) { // Метод для отправки сообщений
        try {
            out.write(value + "\r\n"); // Пишем в поток, \r - перевод каретки на начало строки для корректной работы с программой Putty, \n - перенос каретки на следующую строку
            out.flush(); // Сбрасываем написанное в буфере в поток, если вдруг сообщение застряло в буфере и не передалось по сети
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(); // Раз появилось исключение, то что-то работаем не так, в связи с этим применяем метод disconnect
        }
    }

    public synchronized void disconnect () { // Метод для прерывания связи
        readThread.interrupt(); // Прерываем поток чтения
        try {
            socket.close(); // После прерывания закрываем сокет
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() { // Переопределение метода для вывода нужной информации
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort(); // Вывод в консоль адреса машины сервера и порта, который он занял
    }

}
