package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;
import com.psp.gestionproyecto.model.UserException;
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
import java.util.ArrayList;

public class UserController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Number> userIdColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Boolean> isAdminColumn;
    @FXML
    private TableColumn<User, Number> userGroupIdColumn;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private CheckBox isAdminInput;
    @FXML
    private TextField userGroupIdInput;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label isAdminLabel;
    @FXML
    private Label userGroupIdLabel;

    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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

        userTable.setItems(userList);

        loadUsersFromDB();

        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    private void loadUsersFromDB() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        try {
            ArrayList<UserVO> usersVO = userRepository.load();
            ObservableList<User> users = UserConverter.userVOToUserConverter(usersVO);
            userList.clear();
            userList.addAll(users);
        } catch (UserException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

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

    @FXML
    private void handleAddUser() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/AddUser.fxml"));
            Parent root = loader.load();

            AddUserController controller = loader.getController();

            controller.setUserRepository(new UserRepositoryImpl());

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setDialogStage(stage);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/UpdateUser.fxml"));
                Parent root = loader.load();

                UpdateUserController controller = loader.getController();
                controller.initData(selectedUser);

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update User");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                controller.setDialogStage(stage);

                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please, choose an user to update.");
        }
    }


    private void clearInput() {
        usernameInput.clear();
        emailInput.clear();
        isAdminInput.setSelected(false);
        userGroupIdInput.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}