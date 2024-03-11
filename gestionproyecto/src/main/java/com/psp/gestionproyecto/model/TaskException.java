package com.psp.gestionproyecto.model;

/**
 * The TaskException class represents an exception specific to task-related operations.
 * It extends the Exception class and provides additional methods to handle exceptions.
 */
public class TaskException extends Exception {

    // Message associated with the exception
    private String mensaje;

    /**
     * Default constructor for the TaskException class.
     * Creates a TaskException object with no message.
     */
    public TaskException() {
    }

    /**
     * Parameterized constructor for the TaskException class.
     * Creates a TaskException object with the specified message.
     *
     * @param mensaje The message describing the exception.
     */
    public TaskException(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Method to get the message associated with the exception.
     *
     * @return The message describing the exception.
     */
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
