package com.psp.gestionproyecto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Task {

    private final IntegerProperty taskId;
    private final StringProperty taskName;
    private final ObjectProperty<LocalDate> creationDate;
    private final ObjectProperty<LocalDate> dueDate;
    private final IntegerProperty groupId;

    public Task() {
        this.taskId = new SimpleIntegerProperty();
        this.taskName = new SimpleStringProperty();
        this.creationDate = new SimpleObjectProperty<>();
        this.dueDate = new SimpleObjectProperty<>();
        this.groupId = new SimpleIntegerProperty();
    }

    public Task(int taskId, String taskName, LocalDate creationDate, LocalDate dueDate, int groupId) {
        this.taskId = new SimpleIntegerProperty(taskId);
        this.taskName = new SimpleStringProperty(taskName);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    public ObjectProperty<LocalDate> creationDateProperty() {
        return creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate.set(creationDate);
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

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