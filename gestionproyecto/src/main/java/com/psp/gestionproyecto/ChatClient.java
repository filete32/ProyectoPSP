package com.psp.gestionproyecto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

/**
 * Client application for a simple chat program.
 */
public class ChatClient extends Application {

    private final TextArea chatArea = new TextArea();
    private final TextField messageField = new TextField();
    private PrintWriter writer;

    /**
     * Method called when the application starts.
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the layout for the chat client
        BorderPane root = new BorderPane();
        HBox bottomPane = new HBox(10);
        bottomPane.setPadding(new Insets(10));

        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        bottomPane.getChildren().addAll(messageField, new Button("Send"));
        root.setCenter(chatArea);
        root.setBottom(bottomPane);

        // Configure the primary stage
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Chat Client");
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 8083); // Change "localhost" and 8080 according to your configuration
            writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to listen for messages from the server
            new Thread(() -> {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        chatArea.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Set action for pressing Enter key in message field
            messageField.setOnAction(e -> {
                sendMessage(messageField.getText());
                messageField.clear();
            });

            // Set action for clicking the send button
            ((Button) bottomPane.getChildren().get(1)).setOnAction(e -> {
                sendMessage(messageField.getText());
                messageField.clear();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the server.
     * @param message The message to send.
     */
    private void sendMessage(String message) {
        writer.println(message);
    }

    /**
     * Main method to launch the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
