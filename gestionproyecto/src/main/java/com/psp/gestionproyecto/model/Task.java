package com.psp.gestionproyecto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

/**
 * The Task class represents a task entity in the system.
 * It provides properties for task attributes and methods to access and modify them.
 */
public class Task {

    // Properties representing task attributes
    private final IntegerProperty taskId; // ID of the task
    private final StringProperty taskName; // Name of the task
    private final ObjectProperty<LocalDate> creationDate; // Date when the task was created
    private final ObjectProperty<LocalDate> dueDate; // Due date of the task
    private final IntegerProperty groupId; // ID of the group associated with the task

    /**
     * Default constructor for the Task class.
     * Initializes the properties with default values.
     */
    public Task() {
        this.taskId = new SimpleIntegerProperty();
        this.taskName = new SimpleStringProperty();
        this.creationDate = new SimpleObjectProperty<>();
        this.dueDate = new SimpleObjectProperty<>();
        this.groupId = new SimpleIntegerProperty();
    }

    /**
     * Parameterized constructor for the Task class.
     * Initializes the properties with the specified values.
     *
     * @param taskId        The ID of the task.
     * @param taskName      The name of the task.
     * @param creationDate  The creation date of the task.
     * @param dueDate       The due date of the task.
     * @param groupId       The ID of the group associated with the task.
     */
    public Task(int taskId, String taskName, LocalDate creationDate, LocalDate dueDate, int groupId) {
        this.taskId = new SimpleIntegerProperty(taskId);
        this.taskName = new SimpleStringProperty(taskName);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

    // Getter and setter methods for taskId property
    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    // Getter and setter methods for taskName property
    public StringProperty taskNameProperty() {
        return taskName;
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    // Getter and setter methods for creationDate property
    public ObjectProperty<LocalDate> creationDateProperty() {
        return creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate.set(creationDate);
    }

    // Getter and setter methods for dueDate property
    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    // Getter and setter methods for groupId property
    public IntegerProperty groupIdProperty() {
        return groupId;
    }

    public int getGroupId() {
        return groupId.get();
    }

    public void setGroupId(int groupId) {
        this.groupId.set(groupId);
    }
}
