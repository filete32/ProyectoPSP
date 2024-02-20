package com.psp.gestionproyecto.model;

public class GroupException extends Exception {
    private String mensaje;

    public GroupException() {
    }

    public GroupException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String imprimirMensaje() {
        return this.mensaje;
    }
}