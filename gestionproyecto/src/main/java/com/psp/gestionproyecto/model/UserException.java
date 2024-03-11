package com.psp.gestionproyecto.model;

/**
 * The UserException class represents an exception specific to user-related operations.
 * It extends the Exception class and provides a custom message.
 */
public class UserException extends Exception {

    // Custom error message for the exception
    private String mensaje;

    /**
     * Default constructor for the UserException class.
     * Creates a UserException object with no message.
     */
    public UserException() {
    }

    /**
     * Parameterized constructor for the UserException class.
     * Creates a UserException object with the specified message.
     *
     * @param mensaje The error message describing the exception.
     */
    public UserException(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Method to retrieve the error message associated with the exception.
     *
     * @return The error message.
     */
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
