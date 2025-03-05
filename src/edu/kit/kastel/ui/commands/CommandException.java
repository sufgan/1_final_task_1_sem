package edu.kit.kastel.ui.commands;

public class CommandException extends Exception {
    private static final String MESSAGE_PREFIX = "Command error, ";

    public CommandException(String message) {
        super(MESSAGE_PREFIX + message);
    }
}
