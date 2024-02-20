package com.psp.gestionproyecto.model;

import java.time.LocalDate;

public class TaskVO {

    private int taskId;
    private String taskName;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private int groupId;

    public TaskVO() {
    }

    public TaskVO(int taskId, String taskName, LocalDate creationDate, LocalDate dueDate, int groupId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.groupId = groupId;
    }

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