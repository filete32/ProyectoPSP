package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.*;
import com.psp.gestionproyecto.model.repository.UserRepository;
import com.psp.gestionproyecto.model.repository.impl.TaskRepositoryImpl;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;
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

public class TaskController {

    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, Number> taskIdColumn;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<Task, LocalDate> dueDateColumn;
    @FXML
    private TableColumn<Task, Number> groupIdColumn;
    @FXML
    private Label taskNameLabel;
    @FXML
    private Label creationDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label groupIdLabel;
    @FXML
    private TextField taskNameInput;
    @FXML
    private DatePicker creationDateInput;
    @FXML
    private DatePicker dueDateInput;
    @FXML
    private TextField groupIdInput;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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

        loadTasksFromDB();

        taskTable.setItems(taskList);

        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
                taskNameLabel.setText(selectedTask.getTaskName());
                creationDateLabel.setText(selectedTask.getCreationDate().toString());
                dueDateLabel.setText(selectedTask.getDueDate().toString());
                groupIdLabel.setText(Integer.toString(selectedTask.getGroupId()));
            } else {
                taskNameLabel.setText("");
                creationDateLabel.setText("");
                dueDateLabel.setText("");
                groupIdLabel.setText("");
            }
        });
    }

    private void loadTasksFromDB() {
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        try {
            ArrayList<TaskVO> tasks = taskRepository.load();
            for (TaskVO taskVO : tasks) {
                Task task = new Task(taskVO.getTaskId(), taskVO.getTaskName(), taskVO.getCreationDate(), taskVO.getDueDate(), taskVO.getGroupId());
                taskList.add(task);
            }
        } catch (TaskException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

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

    @FXML
    private void handleAddTask() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/AddTask.fxml"));
            Parent root = loader.load();

            AddTaskController controller = loader.getController();

            controller.setTaskRepository(new TaskRepositoryImpl());

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add Task");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setDialogStage(stage);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                // Obtener el nombre de usuario actual
                String username = System.getProperty("USERNAME");

                // Obtener el repositorio de usuarios
                UserRepository userRepository = new UserRepositoryImpl();

                // Obtener el usuario actual
                UserVO currentUser = userRepository.getUserByUsername(username);

                // Verificar si el usuario es administrador
                boolean isAdmin = userRepository.isAdmin(currentUser);

                if (isAdmin) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/UpdateTask.fxml"));
                    Parent root = loader.load();

                    UpdateTaskController controller = loader.getController();
                    controller.setDialogStage(new Stage());
                    controller.setTaskData(selectedTask.getTaskName(), selectedTask.getCreationDate(), selectedTask.getDueDate(), selectedTask.getGroupId());

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setTitle("Update Task");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.showAndWait();

                    if (controller.isTaskUpdated()) {
                        String updatedTaskName = controller.getTaskNameInput();
                        LocalDate updatedCreationDate = controller.getCreationDateInput();
                        LocalDate updatedDueDate = controller.getDueDateInput();
                        int updatedGroupId = Integer.parseInt(controller.getGroupIdInput());

                        selectedTask.setTaskName(updatedTaskName);
                        selectedTask.setCreationDate(updatedCreationDate);
                        selectedTask.setDueDate(updatedDueDate);
                        selectedTask.setGroupId(updatedGroupId);

                        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
                        TaskVO updatedTaskVO = new TaskVO(selectedTask.getTaskId(), updatedTaskName, updatedCreationDate, updatedDueDate, updatedGroupId);
                        taskRepository.update(updatedTaskVO);

                        taskTable.refresh();
                    }
                } else {
                    // Mostrar mensaje de error si el usuario no es administrador
                    showAlert("Error", "You do not have permission to update tasks.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TaskException | UserException e) {
                e.printStackTrace();
            }
        }
    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearInput() {
        taskNameInput.clear();
        creationDateInput.setValue(null);
        dueDateInput.setValue(null);
        groupIdInput.clear();
    }
}
