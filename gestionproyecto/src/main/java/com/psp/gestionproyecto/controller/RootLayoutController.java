package com.psp.gestionproyecto.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RootLayoutController {

    @FXML
    private void handleViewUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/User.fxml"));
        Parent root = loader.load();
        UserController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Users");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleViewGroups() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/Group.fxml"));
        Parent root = loader.load();
        GroupController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Groups");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
