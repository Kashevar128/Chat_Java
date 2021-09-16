package ru.net.client.gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelChat extends Label {

    private final String stylesOutgoingLabel = "-fx-background-color: #50C984;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public LabelChat(String text) {
        super(text);
        this.setStyle(stylesOutgoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(450);
        this.setWrapText(true);
    }

}
