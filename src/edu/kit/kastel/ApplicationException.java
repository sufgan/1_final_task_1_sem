package edu.kit.kastel;

/**
 * Represents a custom exception type for application-specific errors.
 * <p>
 * This exception is a subclass of {@code Exception} and is used to
 * indicate issues specific to the application's logic or functionality.
 * It automatically prefixes error messages with a predefined string for consistency.
 * </p>
 *
 * @author uyqbd
 */
public class ApplicationException extends Exception {
    private static final String MESSAGE_PREFIX = "Error, ";

    /**
     * Constructs an {@code ApplicationException} with a detailed error message.
     *
     * <p>
     * This custom exception automatically prefixes the provided message with a
     * predefined string to ensure consistency in error reporting.
     * </p>
     *
     * @param message the specific error message that describes the exception
     */
    public ApplicationException(String message) {
        super(MESSAGE_PREFIX + message);
    }

}
