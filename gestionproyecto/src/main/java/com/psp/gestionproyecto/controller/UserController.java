package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.util.UserConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The UserController class controls the user management functionality.
 */
public class UserController {

    @FXML
    private TableView<User> userTable; // Table for displaying users
    @FXML
    private TableColumn<User, Number> userIdColumn; // Column for user ID
    @FXML
    private TableColumn<User, String> usernameColumn; // Column for username
    @FXML
    private TableColumn<User, String> emailColumn; // Column for email
    @FXML
    private TableColumn<User, Boolean> isAdminColumn; // Column for admin status
    @FXML
    private TableColumn<User, Number> userGroupIdColumn; // Column for group ID
    @FXML
    private TextField usernameInput; // Input field for username
    @FXML
    private TextField emailInput; // Input field for email
    @FXML
    private CheckBox isAdminInput; // Checkbox for admin status
    @FXML
    private TextField userGroupIdInput; // Input field for group ID
    @FXML
    private Label userIdLabel; // Label for displaying user ID
    @FXML
    private Label usernameLabel; // Label for displaying username
    @FXML
    private Label emailLabel; // Label for displaying email
    @FXML
    private Label isAdminLabel; // Label for displaying admin status
    @FXML
    private Label userGroupIdLabel; // Label for displaying group ID

    private final ObservableList<User> userList = FXCollections.observableArrayList(); // Observable list of users

    private Client client; // Client object for communicating with the server

    /**
     * Initializes the user interface components.
     */
    @FXML
    private void initialize() {
        // Set cell value factories for table columns
        if (userIdColumn != null) {
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
        if (usernameColumn != null) {
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        }
        if (emailColumn != null) {
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        }
        if (isAdminColumn != null) {
            isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        }
        if (userGroupIdColumn != null) {
            userGroupIdColumn.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        }

        // Load users from the database
        loadUsersFromDB();

        // Listen for selection changes in the user table
        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    /**
     * Loads users from the database and displays them in the table.
     */
    public void loadUsersFromDB() {
        if (client != null){
            userList.addAll(UserConverter.userVOToUserConverter(client.loadUsers()));
            userTable.setItems(userList);
        }
    }

    /**
     * Displays details of the selected user.
     *
     * @param user The selected user.
     */
    private void showUserDetails(User user) {
        if (user != null) {
            userIdLabel.setText(String.valueOf(user.getUserId()));
            usernameLabel.setText(user.getUsername());
            emailLabel.setText(user.getEmail());
            isAdminLabel.setText(String.valueOf(user.isAdmin()));
            userGroupIdLabel.setText(String.valueOf(user.getGroupId()));
        } else {
            userIdLabel.setText("");
            usernameLabel.setText("");
            emailLabel.setText("");
            isAdminLabel.setText("");
            userGroupIdLabel.setText("");
        }
    }

    /**
     * Handles the action for adding a new user.
     * Opens a dialog for adding a new user and updates the user list.
     */
    @FXML
    private void handleAddUser() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/AddUser.fxml"));
            Parent root = loader.load();

            AddUserController controller = loader.getController();

            controller.setClient(client);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setDialogStage(stage);

            stage.showAndWait();

            // After closing the add user window
            if (controller.isUserAdded()) {
                // Add the new user to the observable list
                userList.add(controller.getAddedUser());
                // Refresh the table to display the new user
                userTable.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for updating a user.
     * Opens a dialog for updating the selected user and updates the user list.
     */
    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/UpdateUser.fxml"));
                Parent root = loader.load();

                UpdateUserController controller = loader.getController();
                controller.initData(selectedUser);
                controller.setClient(client);

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update User");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                controller.setDialogStage(stage);

                stage.showAndWait();

                // After closing the add user window
                if (controller.isUserUpdated()) {
                    //Remove the old user from the observable list
                    userList.remove(selectedUser);
                    // Add the new user to the observable list
                    userList.add(controller.getUpdatedUser());
                    // Refresh the table to display the new user
                    userTable.refresh();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please, choose an user to update.");
        }
    }

    /**
     * Clears the input fields.
     */
    private void clearInput() {
        usernameInput.clear();
        emailInput.clear();
        isAdminInput.setSelected(false);
        userGroupIdInput.clear();
    }

    /**
     * Displays an alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the client object for communicating with the server.
     *
     * @param client The client object.
     */
    public void setClient(Client client) {
        this.client = client;
        loadUsersFromDB();
    }
}
