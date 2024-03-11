package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.Task;
import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.util.TaskConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * The AddTaskController class controls the logic for adding a new task.
 */
public class AddTaskController {

    @FXML
    private TextField taskNameInput; // Input field for task name
    @FXML
    private DatePicker dueDateInput; // Date picker for due date
    @FXML
    private TextField groupIdInput; // Input field for group ID

    private Stage dialogStage; // Stage for the dialog window

    private boolean taskAdded = false; // Flag to indicate if a task has been added successfully

    private Client client; // Client instance for communication with the server

    private TaskVO newTask; // Newly created task object

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The dialog stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Checks if a task has been added successfully.
     *
     * @return true if a task has been added, otherwise false.
     */
    public boolean isTaskAdded() {
        return taskAdded;
    }

    /**
     * Handles the event when the "Add Task" button is clicked.
     */
    @FXML
    private void handleAddTask() {
        if (inputIsValid()) {
            // Create a new TaskVO object with the input data
            newTask = new TaskVO(
                    0, // ID is set to 0 as it will be assigned by the server
                    taskNameInput.getText(), // Get task name from input field
                    LocalDate.now(), // Set creation date to current date
                    dueDateInput.getValue(), // Get due date from date picker
                    Integer.parseInt(groupIdInput.getText()) // Parse group ID from input field
            );

            // Send the new task to the server to be saved
            client.saveTask(newTask);
            taskAdded = true; // Set the flag to true indicating successful addition
            dialogStage.close(); // Close the dialog window
        }
    }

    /**
     * Handles the event when the "Cancel" button is clicked.
     */
    @FXML
    private void handleCancelTask() {
        dialogStage.close();
    }

    /**
     * Validates the input data.
     *
     * @return true if the input is valid, otherwise false.
     */
    private boolean inputIsValid() {
        return true;
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
     * Converts the newly added task to a Task object.
     *
     * @return The added task as a Task object.
     */
    public Task getAddedTask() {
        return TaskConverter.taskVoToTaskConverter(newTask);
    }
}
