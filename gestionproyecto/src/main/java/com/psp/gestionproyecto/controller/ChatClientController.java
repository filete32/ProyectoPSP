package com.psp.gestionproyecto.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Controller class for the ChatClient application.
 */
public class ChatClientController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    private PrintWriter writer;
    private BufferedReader reader;

    /**
     * Constructor for the ChatClientController class. Establishes connection with the server.
     */
    public ChatClientController() {
        try {
            Socket socket = new Socket("localhost", 8083); // Change "localhost" and 8080 according to your configuration
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Initialize a thread to listen for messages from the server
            new Thread(this::receiveMessages).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the server.
     */
    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            // Send the message to the server via the socket
            writer.println(message);
            // Display the message in the chat area locally
            appendMessage("You", message);
            // Clear the message field
            messageField.clear();
        }
    }

    /**
     * Listens for messages from the server.
     */
    private void receiveMessages() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                // Display the message received from the server in the chat area
                String[] parts = message.split(":");
                if (parts.length == 2) {
                    appendMessage(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a message to the chat area.
     * @param sender The sender of the message.
     * @param message The message content.
     */
    private void appendMessage(String sender, String message) {
        Platform.runLater(() -> {
            chatArea.appendText(sender + ": " + message + "\n");
        });
    }

    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        // Configure click event for the send button
        sendButton.setOnAction(event -> sendMessage());
    }
}
