package ru.net.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TCPConnection {

    final private Socket socket;
    final private BufferedReader in;
    final private BufferedWriter out;
    final private Thread rxThread;
    final private ListenerNetwork listener;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TCPConnection(ListenerNetwork listener, String address, int port) throws IOException {
        this( listener, new Socket(address, port));
        this.name = "";
    }

    public TCPConnection(ListenerNetwork listener, Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.listener = listener;
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!rxThread.isInterrupted()) {
                        listener.receivingMessage(TCPConnection.this, in.readLine());
                    }
                } catch (IOException e) {
                    listener.exception(TCPConnection.this, e);
                } finally {
                    System.out.println("Соединение разорвано.");
                    disconnect(name);
                }

            }
        });
        rxThread.start();
    }

    public synchronized void sendMessage(String msg) {
        try {
            out.write(msg + "\r\n");
            out.flush();
        } catch (IOException e) {
            listener.exception(TCPConnection.this, e);
            disconnect(name);
        }
    }

    public synchronized void disconnect(String nick) {
        try {
            rxThread.interrupt();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            listener.connectionTerminated(TCPConnection.this, nick);
        }
    }

    @Override
    public String toString() {
        return "TCPconnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }

}
