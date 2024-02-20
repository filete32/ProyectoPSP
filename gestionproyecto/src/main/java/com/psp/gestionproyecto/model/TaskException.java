package com.psp.gestionproyecto.model;

public class TaskException extends Exception {
    private String message;

    public TaskException() {
    }

    public TaskException(String message) {
        this.message = message;
    }

    public String printMessage() {
        return this.message;
    }
}