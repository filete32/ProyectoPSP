package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.Client;
import com.psp.gestionproyecto.model.*;
import com.psp.gestionproyecto.util.TaskConverter;
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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The MainController class controls the main functionality of the application.
 */
public class MainController {

    @FXML
    private TableView<Task> taskTable; // TableView for displaying tasks

    @FXML
    private TableColumn<Task, Number> taskIdColumn; // TableColumn for task IDs

    @FXML
    private TableColumn<Task, String> taskNameColumn; // TableColumn for task names

    @FXML
    private TableColumn<Task, LocalDate> creationDateColumn; // TableColumn for creation dates

    @FXML
    private TableColumn<Task, LocalDate> dueDateColumn; // TableColumn for due dates

    @FXML
    private TableColumn<Task, Number> groupIdColumn; // TableColumn for group IDs

    @FXML
    private Label taskNameLabel; // Label for displaying selected task name

    @FXML
    private Label creationDateLabel; // Label for displaying selected task creation date

    @FXML
    private Label dueDateLabel; // Label for displaying selected task due date

    @FXML
    private Label groupIdLabel; // Label for displaying selected task group ID

    @FXML
    private TextField taskNameInput; // TextField for entering task name

    @FXML
    private DatePicker creationDateInput; // DatePicker for selecting task creation date

    @FXML
    private DatePicker dueDateInput; // DatePicker for selecting task due date

    @FXML
    private TextField groupIdInput; // TextField for entering task group ID

    private final ObservableList<Task> taskList = FXCollections.observableArrayList(); // List of tasks

    private TaskConverter taskConverter; // Task converter utility

    private Client client; // Client for interacting with the backend

    /**
     * Initializes the task table and loads tasks from the database.
     */
    @FXML
    private void initialize() {
        // Set cell value factories for each column
        if (taskIdColumn != null) {
            taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        }
        if (taskNameColumn != null) {
            taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        }
        if (creationDateColumn != null) {
            creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        }
        if (dueDateColumn != null) {
            dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        }
        if (groupIdColumn != null) {
            groupIdColumn.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        }

        if (taskTable != null) {
            taskTable.setItems(taskList);
        }

        // Load tasks from the database
        loadTasksFromDB();

        // Listen for selection changes and display details of the selected task
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
                taskNameLabel.setText(selectedTask.getTaskName());
                creationDateLabel.setText(selectedTask.getCreationDate().toString());
                dueDateLabel.setText(selectedTask.getDueDate().toString());
                groupIdLabel.setText(Integer.toString(selectedTask.getGroupId()));
            } else {
                // Clear labels if no task is selected
                taskNameLabel.setText("");
                creationDateLabel.setText("");
                dueDateLabel.setText("");
                groupIdLabel.setText("");
            }
        });
    }

    /**
     * Loads tasks from the database and populates the task list.
     */
    public void loadTasksFromDB() {
        if (client != null){
            taskList.addAll(TaskConverter.taskVOToTaskConverter(client.loadTasks()));
            taskTable.setItems(taskList);
        }
    }

    /**
     * Handles the action to view tasks.
     *
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void handleViewTasks() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/MainView.fxml"));
        Parent root = loader.load();
        UserController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Tasks");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Handles the action to add a new task.
     */
    @FXML
    private void handleAddTask() {
        try {
            // Load the AddTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/AddTask.fxml"));
            Parent root = loader.load();

            // Get the controller for the AddTask view
            AddTaskController controller = loader.getController();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add Task");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Set the dialog stage and client for the controller
            controller.setDialogStage(stage);
            controller.setClient(client);

            // Show the Add Task window and wait for it to be closed
            stage.showAndWait();

            // After closing the Add Task window
            if (controller.isTaskAdded()) {
                // Add the new task to the observable list
                taskList.add(controller.getAddedTask());
                // Refresh the table to display the new task
                taskTable.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to update the selected task.
     */
    @FXML
    private void handleUpdateTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                String username = System.getProperty("USERNAME");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/UpdateTask.fxml"));
                Parent root = loader.load();

                UpdateTaskController controller = loader.getController();
                controller.setDialogStage(new Stage());
                controller.setTaskData(selectedTask);
                controller.setClient(client);

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update Task");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();

                if (controller.isTaskUpdated()){
                    //Remove the old task from the observable list
                    taskList.remove(selectedTask);
                    // Add the new task to the observable list
                    taskList.add(controller.getUpdatedTask());
                    // Refresh the table to display the new task
                    taskTable.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
           }/* catch (UserException e) {
                e.printStackTrace();
            }

            */
        }
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears input fields.
     */
    private void clearInput() {
        taskNameInput.clear();
        creationDateInput.setValue(null);
        dueDateInput.setValue(null);
        groupIdInput.clear();
    }

    /**
     * Sets the client for interacting with the backend and loads tasks from the database.
     *
     * @param client The client object.
     */
    public void setClient(Client client) {
        this.client = client;
        loadTasksFromDB();
    }
}