package de.fherfurt.taskvault.api.exceptions;

public class MappingException extends Exception {
    /**
     * Constructs a new MappingException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
