package com.psp.gestionproyecto.model;

public class TaskException extends Exception {
    private String mensaje;

    public TaskException() {
    }

    public TaskException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String imprimirMensaje() {
        return this.mensaje;
    }
}