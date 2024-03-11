package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.Task;
import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.util.TaskConverter;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * The UpdateTaskController class controls the update task dialog.
 */
public class UpdateTaskController {

    @FXML
    private TextField taskNameInput; // Input field for task name
    @FXML
    private DatePicker creationDateInput; // Date picker for creation date
    @FXML
    private DatePicker dueDateInput; // Date picker for due date
    @FXML
    private TextField groupIdInput; // Input field for group ID

    private Stage dialogStage; // Stage for the dialog
    private boolean isTaskUpdated = false; // Flag indicating if the task is updated
    private Client client;
    private TaskVO updatedTask;

    private int id;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Checks if the task is updated.
     *
     * @return true if the task is updated, false otherwise.
     */
    public boolean isTaskUpdated() {
        return isTaskUpdated;
    }

    /**
     * Retrieves the task name input from the text field.
     *
     * @return The task name input.
     */
    public String getTaskNameInput() {
        return taskNameInput.getText();
    }

    /**
     * Retrieves the creation date input from the date picker.
     *
     * @return The creation date input.
     */
    public LocalDate getCreationDateInput() {
        return creationDateInput.getValue();
    }

    /**
     * Retrieves the due date input from the date picker.
     *
     * @return The due date input.
     */
    public LocalDate getDueDateInput() {
        return dueDateInput.getValue();
    }

    /**
     * Retrieves the group ID input from the text field.
     *
     * @return The group ID input.
     */
    public String getGroupIdInput() {
        return groupIdInput.getText();
    }

    /**
     * Sets the data of the task to be updated in the input fields.
     *
     * @param task      The ID of the group associated with the task.
     */
    public void setTaskData(Task task) {
        taskNameInput.setText(task.getTaskName());
        creationDateInput.setValue(task.getCreationDate());
        dueDateInput.setValue(task.getDueDate());
        groupIdInput.setText(String.valueOf(task.getGroupId()));

        this.id = task.getTaskId();
    }

    /**
     * Handles the update action.
     * Sets the taskUpdated flag to true and closes the dialog.
     */
    @FXML
    private void handleUpdate() {
        String taskName = taskNameInput.getText();
        LocalDate creationDate = creationDateInput.getValue();
        LocalDate dueDate = dueDateInput.getValue();
        int groupId = Integer.parseInt(groupIdInput.getText());

        updatedTask = new TaskVO(id, taskName, creationDate, dueDate, groupId);
        client.updateTask(updatedTask);
        isTaskUpdated = true;
        dialogStage.close();
    }

    /**
     * Handles the cancel action.
     * Closes the dialog without updating the task.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Task getUpdatedTask() {
        return TaskConverter.taskVoToTaskConverter(updatedTask);
    }
}
