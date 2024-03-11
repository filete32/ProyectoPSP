package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.Group;
import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.util.GroupConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The AddGroupController class controls the logic for adding a new group.
 */
public class AddGroupController {

    @FXML
    private TextField groupIdInput;
    @FXML
    private TextField groupNameInput;
    @FXML
    private TextField groupDescriptionInput;


    private Client client;
    private Stage dialogStage; // Stage for the dialog window

    private boolean isGroupAdded = false; // Flag to indicate if a group has been added successfully
    private GroupVO addedGroup;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Checks if a group has been added successfully.
     *
     * @return true if a group has been added, otherwise false.
     */

    /**
     * Handles the event when the "Add Group" button is clicked.
     */
    @FXML
    private void handleAddGroup() {
        // Create a new GroupVO object with the input data
        addedGroup = new GroupVO(
                Integer.parseInt(groupIdInput.getText()),
                groupNameInput.getText(),
                groupDescriptionInput.getText()
        );

        // Save the new group using the group repository
        client.saveGroup(addedGroup);
        isGroupAdded = true; // Set the flag to true indicating successful addition
        dialogStage.close(); // Close the dialog window
    }

    /**
     * Validates the input data.
     *
     * @return true if the input is valid, otherwise false.
     */

    /**
     * Sets the group repository.
     *
     * @param groupRepository The group repository to set.
     */

    /**
     * Handles the event when the "Cancel" button is clicked.
     *
     * @param actionEvent The ActionEvent representing the event.
     */
    public void handleCancelGroup(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public boolean isGroupAdded() {
        return isGroupAdded;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Group getAddedGroup() {
        return GroupConverter.groupVOToGroupConverter(addedGroup);
    }
}
