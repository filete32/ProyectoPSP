package com.psp.gestionproyecto.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UpdateTaskController {

    @FXML
    private TextField taskNameInput;
    @FXML
    private DatePicker creationDateInput;
    @FXML
    private DatePicker dueDateInput;
    @FXML
    private TextField groupIdInput;

    private Stage dialogStage;
    private boolean taskUpdated = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isTaskUpdated() {
        return taskUpdated;
    }

    public String getTaskNameInput() {
        return taskNameInput.getText();
    }

    public LocalDate getCreationDateInput() {
        return creationDateInput.getValue();
    }

    public LocalDate getDueDateInput() {
        return dueDateInput.getValue();
    }

    public String getGroupIdInput() {
        return groupIdInput.getText();
    }

    public void setTaskData(String taskName, LocalDate creationDate, LocalDate dueDate, int groupId) {
        taskNameInput.setText(taskName);
        creationDateInput.setValue(creationDate);
        dueDateInput.setValue(dueDate);
        groupIdInput.setText(String.valueOf(groupId));
    }

    @FXML
    private void handleUpdate() {
        taskUpdated = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
