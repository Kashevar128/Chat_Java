package ru.net.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPConnection {

    private final Socket socket;
    private Thread readThread;
    private final TCPConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection(Socket socket, TCPConnectionListener eventListener) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        this.eventListener = eventListener;
        readThread = new Thread( () -> {
            try {
                eventListener.onConnectionReady(TCPConnection.this);
                while (!readThread.isInterrupted()) {
                    String msg = in.readLine();
                    eventListener.onReceiveString(TCPConnection.this, msg);
                }
            } catch (IOException e) {

            } finally {

            }
        });
        readThread.start();
    }

    public synchronized void sendMessage (String value) {
        try {
            out.write(value);

        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect () {
        readThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }
}
