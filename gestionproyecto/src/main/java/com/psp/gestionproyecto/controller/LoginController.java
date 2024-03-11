package com.psp.gestionproyecto.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

import com.psp.gestionproyecto.util.UserPreferences;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.util.Logger; // Importa la clase Logger

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage;
    private Client client; // Instance of Client
    private UserPreferences userPreferences;
    private Logger logger; // Instance of Logger

    public LoginController() {
        this.userPreferences = new UserPreferences();
        this.logger = new Logger(); // Inicializa Logger
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleLoginButtonAction() {
        String nom_usuario = usernameField.getText();
        String password = passwordField.getText();

        if (login(nom_usuario, password)) {
            if (client != null) {
                userPreferences.saveUsername(nom_usuario);
                client.mailNotify();
                client.launchMainApplication(primaryStage);
            }
            // Registra la operación de inicio de sesión en el archivo de log
            logger.log(nom_usuario, "Login");
            primaryStage.close();
        } else {
            showErrorDialog("Incorrect credentials", "Incorrect username or password. Please try again.");
        }
    }

    private boolean login(String nom_usuario, String password) {
        return client.login(nom_usuario, password);
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}