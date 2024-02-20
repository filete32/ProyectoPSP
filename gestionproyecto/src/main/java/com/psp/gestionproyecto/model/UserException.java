package com.psp.gestionproyecto.model;

public class UserException extends Exception {
    private String message;

    public UserException() {
    }

    public UserException(String message) {
        this.message = message;
    }

    public String printMessage() {
        return this.message;
    }
}