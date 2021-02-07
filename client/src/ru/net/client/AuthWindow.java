package ru.net.client;


import ru.net.network.TCPConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AuthWindow extends ClientWindow {
    TCPConnection connection = null;
    private static final int width = 300;
    private static final int heith = 150;
    private final JTextField login = new JTextField();
    private final JTextField password = new JTextField();
    private final JLabel jlab_1 = new JLabel("Login");
    private final JLabel jlab_2 = new JLabel("Password");

    AuthWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, heith);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        login.addActionListener(this);
        password.addActionListener(this);

        Box box = Box.createVerticalBox();
        box.add(jlab_1);
        box.add(login);
        box.add(jlab_2);
        box.add(password);
        setResizable(false);

        add(box);

        setVisible(true);

        log.setVisible(false);
        fieldInput.setVisible(false);
        fieldNickname.setVisible(false);

        connection = getConnection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthWindow auth = new AuthWindow();
                ClientWindow client = new ClientWindow();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onAuthClick();
        if (isAuthorized()) {
            dispose();
        }
    }

    public void onAuthClick() {
        String msg = login.getText();
        String msg_2 = password.getText();
        if (msg.equals("") || msg_2.equals("")) return;
        login.setText(null);
        password.setText(null);
        connection.sendMessage("/auth " + msg + " " + msg_2);
    }


}
