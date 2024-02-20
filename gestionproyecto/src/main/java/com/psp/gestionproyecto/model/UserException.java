package com.psp.gestionproyecto.model;

public class UserException extends Exception {
    private String mensaje;

    public UserException() {
    }

    public UserException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String imprimirMensaje() {
        return this.mensaje;
    }
}