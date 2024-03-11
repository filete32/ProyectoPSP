package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.ChatServer;
import com.psp.gestionproyecto.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The RootLayoutController class controls the root layout of the application.
 */
public class RootLayoutController {

    private Client client = new Client(); // Client instance

    /**
     * Handles viewing users.
     * Opens a new window to display users.
     *
     * @throws IOException If an error occurs during loading the view.
     */
    @FXML
    private void handleViewUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/User.fxml"));
        Parent root = loader.load();
        UserController controller = loader.getController();
        controller.setClient(client);

        Stage stage = new Stage();
        stage.setTitle("Users");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Handles viewing groups.
     * Opens a new window to display groups.
     *
     * @throws IOException If an error occurs during loading the view.
     */
    @FXML
    private void handleViewGroups() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/Group.fxml"));
        Parent root = loader.load();
        GroupController controller = loader.getController();
        controller.setClient(client);

        Stage stage = new Stage();
        stage.setTitle("Groups");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Handles opening the chat.
     * Opens a new window for the chat feature.
     *
     * @throws IOException If an error occurs during loading the view.
     */
    @FXML
    private void handleOpenChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/ChatClient.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStartChatServer() {
        // Inicia el servidor de chat
        ChatServer chatServer = new ChatServer();
        Stage stage = new Stage();
        chatServer.start(stage);
    }

    @FXML
    private void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chat Help");
        alert.setHeaderText("How to use the chat:");
        alert.setContentText("1. Start the Chat Server by selecting 'Start Chat Server' from the Chat Menu.\n" +
                "2. Once the server is started, open the chat by selecting 'Open Chat' from the Chat Menu.");
        alert.showAndWait();
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Retrieves the client instance.
     *
     * @return The client instance.
     */
    public Client getClient() {
        return client;
    }
}
