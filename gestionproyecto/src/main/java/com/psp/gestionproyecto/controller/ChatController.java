package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The ChatController class controls the chat functionality.
 */
public class ChatController {

    @FXML
    private TextArea chatTextArea; // TextArea for displaying chat messages

    @FXML
    private TextField messageField; // TextField for entering chat messages

    private Client client; // Client instance for communication with the server

    /**
     * Sends the message entered in the message field.
     * Appends the message to the chat text area.
     * Clears the message field after sending the message.
     */
    @FXML
    private void sendMessage() {
        String message = messageField.getText(); // Get the message from the message field
        chatTextArea.appendText("You: " + message + "\n"); // Append the message to the chat text area
        messageField.clear(); // Clear the message field after sending the message
    }

    /**
     * Sets the client instance for communication with the server.
     *
     * @param client The client instance to set.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Closes the chat window.
     */
    @FXML
    private void closeChat() {
        messageField.getScene().getWindow().hide(); // Hide the chat window
    }
}
