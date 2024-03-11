package com.psp.gestionproyecto.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The TaskVO class represents a task value object containing task-related information.
 * It implements the Serializable interface for serialization purposes.
 */
public class TaskVO implements Serializable {

    // Task identifier
    private int taskId;

    // Task name
    private String taskName;

    // Date when the task was created
    private LocalDate creationDate;

    // Due date for completing the task
    private LocalDate dueDate;

    // Identifier of the group to which the task belongs
    private int groupId;

    /**
     * Default constructor for the TaskVO class.
     * Creates a TaskVO object with default attribute values.
     */
    public TaskVO() {
    }

    /**
     * Parameterized constructor for the TaskVO class.
     * Creates a TaskVO object with the specified attribute values.
     *
     * @param taskId       The unique identifier for the task.
     * @param taskName     The name of the task.
     * @param creationDate The date when the task was created.
     * @param dueDate      The due date for completing the task.
     * @param groupId      The identifier of the group to which the task belongs.
     */
    public TaskVO(int taskId, String taskName, LocalDate creationDate, LocalDate dueDate, int groupId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.groupId = groupId;
    }

    // Getters and setters for the attributes

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
