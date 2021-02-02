package ru.net.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPConnection {

    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    Thread rxThread;
    ListenerNetwork listener;

    public TCPConnection(ListenerNetwork listener, InetAddress address, int port) throws IOException {
        this(new Socket(address, port), listener);
    }

    public TCPConnection(Socket socket, ListenerNetwork listener) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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


    public void sendMessage(String msg) {
        try {
            out.write(msg + "\r\n");
            out.flush();
        } catch (IOException e) {
            listener.exception(TCPConnection.this, e);
            disconnect();
        }
    }

    public void disconnect() {
        try {
            rxThread.interrupt();
            socket.close();
        } catch (IOException e) {
            listener.connectionTerminated(TCPConnection.this);
        }
    }

    @Override
    public String toString() {
        return "TCPconnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
