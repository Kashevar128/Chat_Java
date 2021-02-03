package ru.net.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TCPConnection {
    private String name;
    final private Socket socket;
    final private BufferedReader in;
    final private BufferedWriter out;
    final private Thread rxThread;
    final private ListenerNetwork listener;

    public TCPConnection(ListenerNetwork listener, String address, int port, String name) throws IOException {
        this(new Socket(address, port), listener);
        this.name = name;
    }

    public TCPConnection(Socket socket, ListenerNetwork listener) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.listener = listener;
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listener.connectionIsReady(TCPConnection.this);
                try {
                    while (!rxThread.isInterrupted()) {
                        listener.receivingMessage(TCPConnection.this, in.readLine());
                    }
                } catch (IOException e) {
                    listener.exception(TCPConnection.this, e);
                } finally {
                    System.out.println("Соединение разорвано.");
                    disconnect();
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
            disconnect();
        }
    }

    public synchronized void disconnect() {
        try {
            rxThread.interrupt();
            socket.close();
        } catch (IOException e) {
            listener.connectionTerminated(TCPConnection.this);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TCPconnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
