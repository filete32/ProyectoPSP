package com.psp.gestionproyecto.util;

import com.psp.gestionproyecto.model.Task;
import com.psp.gestionproyecto.model.TaskVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The TaskConverter class provides utility methods to convert between Task and TaskVO objects.
 */
public class TaskConverter {

    /**
     * Converts a list of TaskVO objects to a list of Task objects.
     *
     * @param list List of TaskVO to convert.
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
     * Converts a Task object to a TaskVO object.
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

    /**
     * Converts a TaskVO object to a Task object.
     *
     * @param taskVO The TaskVO to convert.
     * @return The resulting Task.
     */
    public static Task taskVoToTaskConverter(TaskVO taskVO) {
        int taskId = taskVO.getTaskId();
        String taskName = taskVO.getTaskName();
        LocalDate creationDate = taskVO.getCreationDate();
        LocalDate dueDate = taskVO.getDueDate();
        int groupId = taskVO.getGroupId();
        return new Task(taskId, taskName, creationDate, dueDate, groupId);
    }
}
