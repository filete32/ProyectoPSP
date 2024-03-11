package com.psp.gestionproyecto.model;

/**
 * The GroupException class represents an exception specific to group-related operations.
 * It extends the Exception class.
 */
public class GroupException extends Exception {
    private String mensaje; // Message associated with the exception

    /**
     * Default constructor for the GroupException class.
     * Initializes the message with a default value.
     */
    public GroupException() {
    }

    /**
     * Parameterized constructor for the GroupException class.
     * Initializes the message with the specified value.
     *
     * @param mensaje The message associated with the exception.
     */
    public GroupException(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Method to retrieve the message associated with the exception.
     *
     * @return The message associated with the exception.
     */
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
