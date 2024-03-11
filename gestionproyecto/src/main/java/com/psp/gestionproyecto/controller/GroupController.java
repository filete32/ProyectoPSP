package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.Group;
import com.psp.gestionproyecto.model.GroupException;
import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.repository.GroupRepository;
import com.psp.gestionproyecto.model.repository.UserRepository;
import com.psp.gestionproyecto.model.repository.impl.GroupRepositoryImpl;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;
import com.psp.gestionproyecto.util.GroupConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * The GroupController class controls the functionality related to groups.
 */
public class GroupController {

    @FXML
    private TableView<Group> groupTable; // TableView for displaying groups
    @FXML
    private TableColumn<Group, Number> idColumn; // TableColumn for group IDs
    @FXML
    private TableColumn<Group, String> groupNameColumn; // TableColumn for group names
    @FXML
    private TableColumn<Group, String> descriptionColumn; // TableColumn for group descriptions
    @FXML
    private TextField groupNameInput; // TextField for entering group names
    @FXML
    private TextField groupDescriptionInput; // TextField for entering group descriptions
    @FXML
    private Label groupNameLabel; // Label for displaying selected group name
    @FXML
    private Label groupDescriptionLabel; // Label for displaying selected group description
    @FXML
    private Label groupIdLabel; // Label for displaying selected group ID

    private final ObservableList<Group> groupList = FXCollections.observableArrayList(); // ObservableList of groups

    private Client client;

    /**
     * Initializes the GroupController.
     * Sets up the TableView and loads groups from the database.
     */
    @FXML
    private void initialize() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id_group"));
        }
        if (groupNameColumn != null) {
            groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("group_name"));
        }
        if (descriptionColumn != null) {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("group_description"));
        }

        groupTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        groupTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                if (newValue != null) {
                    showSelectedGroup(newValue);
                }
            }
        });
    }

    /**
     * Displays the details of the selected group.
     *
     * @param group The selected group.
     */
    private void showSelectedGroup(Group group) {
        groupIdLabel.setText(String.valueOf(group.getId_group()));
        groupNameLabel.setText(group.getGroup_name());
        groupDescriptionLabel.setText(group.getGroup_description());
    }

    /**
     * Loads groups from the database and populates the TableView.
     */
    private void loadGroupFromDB() {
        if (client != null){
            groupList.addAll(GroupConverter.groupVOToGroupConverter(client.loadGroups()));
            groupTable.setItems(groupList);
        }
    }

    /**
     * Handles adding a new group.
     * Opens a dialog for adding a group.
     */
    @FXML
    private void handleAddGroup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/AddGroup.fxml"));
            Parent root = loader.load();

            AddGroupController controller = loader.getController();

            controller.setClient(client);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add Group");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setDialogStage(stage);

            stage.showAndWait();

            if (controller.isGroupAdded()){
                //Add the new user to the observable list
                groupList.add(controller.getAddedGroup());
                // Refresh the table to display the new user
                groupTable.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles updating the selected group.
     * Opens a dialog for updating the group details.
     */
    @FXML
    private void handleUpdateGroup() {
        Group groupSelected = groupTable.getSelectionModel().getSelectedItem();
        if (groupSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/UpdateGroup.fxml"));
                Parent root = loader.load();

                UpdateGroupController controller = loader.getController();
                controller.setDialogStage(new Stage());
                controller.setGroupData(groupSelected);
                controller.setClient(client);

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update Group");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();

                // After closing the add group window
                if (controller.isGroupUpdated()) {
                    //Remove the old group from the observable list
                    groupList.remove(groupSelected);
                    // Add the new user to the observable list
                    groupList.add(controller.getUpdatedGroup());
                    // Refresh the table to display the new group
                    groupTable.refresh();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void setClient(Client client) {
        this.client = client;
        loadGroupFromDB();
    }
}
