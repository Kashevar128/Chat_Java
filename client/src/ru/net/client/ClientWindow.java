package ru.net.client;

import ru.net.network.ListenerNetwork;
import ru.net.network.TCPConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, ListenerNetwork {
    TCPConnection connection = null;
    private boolean authorized = false;
    private static final String IP = "192.168.0.104";
    private static final int PORT = 8189;
    final int WIDTH = 400;
    final int HEITH = 200;
    final JTextArea log = new JTextArea();
    final JTextField fieldNickname = new JTextField();
    final JTextField fieldInput = new JTextField();

    public boolean isAuthorized() {
        return authorized;
    }
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public TCPConnection getConnection() {
        return connection;
    }

    ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEITH);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        fieldInput.addActionListener(this);
        fieldNickname.setEditable(false);
        add(fieldNickname, BorderLayout.NORTH);
        add(log, BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);
        setResizable(false);

        setVisible(false);

        try {
            connection = new TCPConnection(this, IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        send();
    }

    public void send() {
        String msg = fieldInput.getText();
        if (msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendMessage(fieldNickname.getText() + ": " + msg);

    }

    @Override
    public void connectionIsReady(TCPConnection tcpConnection, String name) {
        printMsg("Соединение установлено...");
    }

    @Override
    public void connectionTerminated(TCPConnection tcpConnection, String name) {
        printMsg("Соединение закрыто.");
    }

    @Override
    public void receivingMessage(TCPConnection tcpConnection, String msg) {
        authentication(tcpConnection, msg);
        printMsg(msg);
    }
    @Override
    public void exception(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exeption: " + e);
    }

    @Override
    public void authentication(TCPConnection tcpConnection, String msg) {
        if (msg.startsWith("/authok")) {
            String[] parts = msg.split("\\s");
            String nick = parts[1];
            fieldNickname.setText(nick);
            setAuthorized(true);
            setVisible(true);
        }
    }
}


