package ru.net.client;

import ru.net.network.TCPConnection;
import ru.net.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP_ADDR = "192.168.0.104";
    private static final int PORT = 8189;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientWindow();
        });
    }

    private final JTextArea textArea = new JTextArea();
    private final JTextField fieldNickname = new JTextField("alex");
    private final JTextField fieldInput = new JTextField();

    private TCPConnection connection;

    private ClientWindow() {
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setSize(WIDTH, HEIGHT);
       setLocationRelativeTo(null);
       setAlwaysOnTop(true);
       textArea.setEditable(false);
       textArea.setLineWrap(true);
       fieldInput.addActionListener(this);
       add(textArea, BorderLayout.CENTER);
       add(fieldInput, BorderLayout.SOUTH);
       add(fieldNickname, BorderLayout.NORTH);

       setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            this.onException(connection, e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendMessage(fieldNickname.getText() + ": " + msg);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
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

    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(msg + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
