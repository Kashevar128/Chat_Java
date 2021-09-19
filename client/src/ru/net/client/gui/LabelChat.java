package ru.net.client.gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class LabelChat extends Label {

    private final String stylesOutGoingLabel = "-fx-background-color: #50C984;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    private final String stylesInGoingLabel = "-fx-background-color: #2D87BF;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public LabelChat(String text, boolean inOrOut) {
        super(text);
        if(inOrOut) {
            this.setStyle(stylesInGoingLabel);
        }
        if(!inOrOut) this.setStyle(stylesOutGoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(450);
        this.setWrapText(true);
    }


}
