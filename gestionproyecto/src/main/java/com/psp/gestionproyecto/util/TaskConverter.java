package com.psp.gestionproyecto.util;

import com.psp.gestionproyecto.model.Task;
import com.psp.gestionproyecto.model.TaskVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskConverter {

    /**
     * Convert a list of TaskVO to a list of Task.
     *
     * @param list The list of TaskVO to convert.
     * @return An ObservableList of Task.
     */
    public static ObservableList<Task> taskVOToTaskConverter(ArrayList<TaskVO> list) {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        for (TaskVO taskVO : list) {
            Task task = new Task();
            task.setTaskId(taskVO.getTaskId());
            task.setTaskName(taskVO.getTaskName());
            task.setCreationDate(taskVO.getCreationDate());
            task.setDueDate(taskVO.getDueDate());
            task.setGroupId(taskVO.getGroupId());
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * Convert a Task to a TaskVO.
     *
     * @param task The Task to convert.
     * @return The resulting TaskVO.
     */
    public static TaskVO taskToTaskVOConverter(Task task) {
        int taskId = task.getTaskId();
        String taskName = task.getTaskName();
        LocalDate creationDate = task.getCreationDate();
        LocalDate dueDate = task.getDueDate();
        int groupId = task.getGroupId();
        return new TaskVO(taskId, taskName, creationDate, dueDate, groupId);
    }
}
