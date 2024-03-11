package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.util.UserConverter;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The UpdateUserController class controls the update user dialog.
 */
public class UpdateUserController {

    @FXML
    private TextField usernameInput; // Input field for username
    @FXML
    private PasswordField passwordInput; // Password field for password
    @FXML
    private CheckBox isAdmin; // Checkbox for admin status
    @FXML
    private TextField emailInput; // Input field for email
    @FXML
    private TextField groupIdInput; // Input field for group ID
    private int id; // User ID

    private Stage dialogStage; // Stage for the dialog
    private Client client; // Client object for communicating with the server
    private boolean isUerUpdated = false;
    UserVO updatedUser;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Handles the update action.
     * Validates the input data and updates the user information.
     */
    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            updateUserData();
        }
    }

    /**
     * Updates the user data based on the input fields.
     */
    private void updateUserData() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String email = emailInput.getText();
        boolean admin = isAdmin.isSelected();
        int groupId = Integer.parseInt(groupIdInput.getText());

        updatedUser = new UserVO(id, username, password, email, admin, groupId);

        client.updateUser(updatedUser);
        isUerUpdated = true;
        dialogStage.close();
    }

    /**
     * Initializes the user data in the input fields.
     *
     * @param user The user whose data will be displayed.
     */
    public void initData(User user) {
        usernameInput.setText(user.getUsername());
        emailInput.setText(user.getEmail());
        isAdmin.setSelected(user.isAdmin());
        groupIdInput.setText(String.valueOf(user.getGroupId()));

        this.id = user.getUserId();
    }

    /**
     * Validates the input data.
     * Currently, it always returns true.
     *
     * @return true if the input data is valid, false otherwise.
     */
    private boolean isInputValid() {
        return true;
    }

    /**
     * Handles the cancel action.
     * Closes the dialog without updating the user.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Sets the client object for communicating with the server.
     *
     * @param client The client object.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isUserUpdated() {
        return isUerUpdated;
    }

    public User getUpdatedUser() {
        return UserConverter.userVOTOUserConverter(updatedUser);
    }
}
