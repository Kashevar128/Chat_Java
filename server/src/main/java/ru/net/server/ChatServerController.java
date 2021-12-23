package ru.net.server;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.io.*;

public class ChatServerController {

    private javafx.event.EventHandler<WindowEvent> closeEventHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ChatServer.getFileReservServer()))) {
                oos.writeObject(ChatServer.getMessages());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }
}
