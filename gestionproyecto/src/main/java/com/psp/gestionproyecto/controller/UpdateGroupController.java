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
 * The UpdateGroupController class controls the update group dialog.
 */
public class UpdateGroupController {

    @FXML
    private TextField groupNameInput, groupDescriptionInput; // Input fields for group name and description

    private Stage dialogStage; // Stage for the dialog

    private boolean isGroupUpdated = false; // Flag indicating if the group is updated

    private Client client;

    private GroupVO updatedGroup;

    private int id;

    /**
     * Sets the data of the group to be updated in the input fields.
     *
     * @param group        The name of the group.
     */
    public void setGroupData(Group group) {
        groupNameInput.setText(group.getGroup_name());
        groupDescriptionInput.setText(group.getGroup_description());
        this.id = group.getId_group();
    }

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Checks if the group is updated.
     *
     * @return true if the group is updated, false otherwise.
     */
    public boolean isGroupUpdated() {
        return isGroupUpdated;
    }

    /**
     * Handles the update action.
     * Sets the groupUpdated flag to true and closes the dialog.
     */
    @FXML
    public void handleUpdate() {

        String groupName = groupNameInput.getText();
        String groupDesc = groupDescriptionInput.getText();

        updatedGroup = new GroupVO(id, groupName, groupDesc);

        client.saveGroup(updatedGroup);
        isGroupUpdated = true;
        dialogStage.close();
    }

    /**
     * Handles the cancel action.
     * Closes the dialog without updating the group.
     */
    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Group getUpdatedGroup() {
        return GroupConverter.groupVOToGroupConverter(updatedGroup);
    }
}
