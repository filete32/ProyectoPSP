package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController {

    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField groupIdInput;

    private UserRepositoryImpl userRepository;

    private Stage dialogStage;

    private boolean userAdded = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isUserAdded() {
        return userAdded;
    }

    @FXML
    private void handleAddUser() {
        if (inputIsValid()) {
            UserVO newUser = new UserVO(
                    0,
                    usernameInput.getText(),
                    passwordInput.getText(),
                    emailInput.getText(),
                    false,
                    Integer.parseInt(groupIdInput.getText())
            );

            try {
                userRepository.save(newUser);
                userAdded = true;
                dialogStage.close();
            } catch (UserException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    private void handleCancelUser() {
        dialogStage.close();
    }

    private boolean inputIsValid() {
        return true;
    }

    public void setUserRepository(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }
}
