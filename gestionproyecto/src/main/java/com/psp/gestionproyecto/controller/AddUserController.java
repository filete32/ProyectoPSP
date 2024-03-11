package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.util.UserConverter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The AddUserController class controls the logic for adding a new user.
 */
public class AddUserController {

    @FXML
    private TextField usernameInput; // Input field for username
    @FXML
    private TextField passwordInput; // Input field for password
    @FXML
    private TextField emailInput; // Input field for email
    @FXML
    private TextField groupIdInput; // Input field for group ID

    private Stage dialogStage; // Stage for the dialog window

    private boolean userAdded = false; // Flag to indicate if a user has been added successfully

    private Client client; // Client instance for communication with the server

    private UserVO newUser; // Newly created user object

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Checks if a user has been added successfully.
     *
     * @return true if a user has been added, otherwise false.
     */
    public boolean isUserAdded() {
        return userAdded;
    }

    /**
     * Sets the client instance.
     *
     * @param client The client instance to set.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Handles the event when the "Add User" button is clicked.
     */
    @FXML
    private void handleAddUser() {
        if (inputIsValid()) {
            // Create a new UserVO object with the input data
            newUser = new UserVO(
                    0, // ID is set to 0 as it will be assigned by the server
                    usernameInput.getText(), // Get username from input field
                    passwordInput.getText(), // Get password from input field
                    emailInput.getText(), // Get email from input field
                    false, // Set isAdmin to false for new users
                    Integer.parseInt(groupIdInput.getText()) // Parse group ID from input field
            );

            // Send the new user to the server to be saved
            client.saveUser(newUser);
            userAdded = true; // Set the flag to true indicating successful addition
            dialogStage.close(); // Close the dialog window
        }
    }

    /**
     * Handles the event when the "Cancel" button is clicked.
     */
    @FXML
    private void handleCancelUser() {
        dialogStage.close(); // Close the dialog stage without adding a user
    }

    /**
     * Validates the input data.
     *
     * @return true if the input is valid, otherwise false.
     */
    private boolean inputIsValid() {
        return true; // Currently no validation, always return true
    }


    /**
     * Converts the newly added user to a User object.
     *
     * @return The added user as a User object.
     */
    public User getAddedUser() {
        return UserConverter.userVOTOUserConverter(newUser);
    }
}
