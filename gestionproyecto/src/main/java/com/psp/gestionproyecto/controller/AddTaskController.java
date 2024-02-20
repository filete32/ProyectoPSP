package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.model.TaskException;
import com.psp.gestionproyecto.model.repository.impl.TaskRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddTaskController {

    @FXML
    private TextField taskNameInput;
    @FXML
    private DatePicker dueDateInput;
    @FXML
    private TextField groupIdInput;

    private TaskRepositoryImpl taskRepository;

    private Stage dialogStage;

    private boolean taskAdded = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isTaskAdded() {
        return taskAdded;
    }

    @FXML
    private void handleAddTask() {
        if (inputIsValid()) {
            TaskVO newTask = new TaskVO(
                    0,
                    taskNameInput.getText(),
                    LocalDate.now(),
                    dueDateInput.getValue(),
                    Integer.parseInt(groupIdInput.getText())
            );

            try {
                taskRepository.save(newTask);
                taskAdded = true;
                dialogStage.close();
            } catch (TaskException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancelTask() {
        dialogStage.close();
    }

    private boolean inputIsValid() {
        return true;
    }

    public void setTaskRepository(TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }
}
