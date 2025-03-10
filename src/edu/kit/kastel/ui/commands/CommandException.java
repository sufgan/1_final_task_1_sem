package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ApplicationException;

/**
 * Exception thrown when a command error occurs.
 * <p>
 * This exception prefixes the provided error message with a standard message.
 * </p>
 *
 * @author uyqbd
 */
public class CommandException extends ApplicationException {

    /**
     * Constructs a new CommandException with the specified error message.
     *
     * @param message the error message to be prefixed and passed to the superclass
     */
    public CommandException(String message) {
        super(message);
    }

}
