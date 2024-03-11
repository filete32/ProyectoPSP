package com.psp.gestionproyecto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server application for a simple chat program.
 */
public class ChatServer extends Application {

    private final TextArea serverLog = new TextArea();
    private final List<ClientHandler> clients = new ArrayList<>();

    /**
     * Method called when the application starts.
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the layout for the server log
        BorderPane root = new BorderPane();
        root.setCenter(serverLog);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Chat Server");
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.hide();
            e.consume();
        });
        primaryStage.show();

        // Start a thread to accept client connections
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8083); // Listen on port 8083
                serverLog.appendText("Server started on port 8083.\n");

                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(socket);
                    clients.add(client);
                    client.start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Inner class representing a client handler thread.
     */
    class ClientHandler extends Thread {
        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;

        /**
         * Constructor for ClientHandler.
         * @param socket The client socket.
         * @throws IOException If an I/O error occurs.
         */
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }

        /**
         * Method called when the thread starts.
         */
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Sends a message to the client.
         * @param message The message to send.
         */
        public void sendMessage(String message) {
            writer.println(message);
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message The message to broadcast.
     */
    private void broadcast(String message) {
        serverLog.appendText("Message received: " + message + "\n");
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * Main method to launch the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
