package com.psp.gestionproyecto.model;

public class GroupException extends Exception {
    private String message;

    public GroupException() {
    }

    public GroupException(String message) {
        this.message = message;
    }

    public String printMessage() {
        return this.message;
    }
}