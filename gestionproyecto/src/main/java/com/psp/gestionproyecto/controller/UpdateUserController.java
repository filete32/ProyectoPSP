package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateUserController {

    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField groupIdInput;

    private Stage dialogStage;
    private UserVO userToUpdate;
    private UserRepositoryImpl userRepository;
    private UserVO UserToUpdate;

    // Constructor
    public UpdateUserController() {
        userRepository = new UserRepositoryImpl();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUserToUpdate(UserVO user) {
        this.userToUpdate = user;
        if (user == null) {
            usernameInput.setText(user.getUsername());
            passwordInput.setText(user.getPassword());
            emailInput.setText(user.getEmail());
            groupIdInput.setText(String.valueOf(user.getGroupId()));
            isAdmin.setSelected(user.isAdmin());
        }
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            updateUserData();
        }
    }

    private void updateUserData() {
        if (userToUpdate == null) {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            boolean admin = isAdmin.isSelected();
            int groupId = Integer.parseInt(groupIdInput.getText());

            UserVO updatedUser = new UserVO(userToUpdate.getUserId(), username, password, email, admin, groupId);

            try {
                userRepository.update(updatedUser);
                dialogStage.close();
            } catch (UserException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData(User user) {
        usernameInput.setText(user.getUsername());
        emailInput.setText(user.getEmail());
        isAdmin.setSelected(user.isAdmin());
        groupIdInput.setText(String.valueOf(user.getGroupId()));
    }


    private boolean isInputValid() {
        return true;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
