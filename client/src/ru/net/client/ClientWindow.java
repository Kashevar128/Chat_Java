package ru.net.client;

import ru.net.network.ListenerNetwork;
import ru.net.network.TCPConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;

public class ClientWindow extends JFrame implements ActionListener, ListenerNetwork {

    private static final String IP = "192.168.0.104";
    private static final int PORT = 8189;
    private static final int WIDHT = 600;
    private static final int HEIGHT = 400;
    private static final String NAME = "Slava";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField(NAME);
    private final JTextField fieldInput = new JTextField();

    private TCPConnection connection;

    private ClientWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDHT, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);
        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP, PORT, NAME);
        } catch (IOException e) {
            printMsg("TCPConnection exeption: " + e);
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
    public void connectionIsReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    @Override
    public void connectionTerminated(TCPConnection tcpConnection) {
        printMsg("Connection close.");
    }

    @Override
    public void receivingMessage(TCPConnection tcpConnection, String msg) {
        printMsg(msg);
    }

    @Override
    public void exception(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exeption: " + e);
    }

    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
