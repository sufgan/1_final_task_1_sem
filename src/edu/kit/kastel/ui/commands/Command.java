package edu.kit.kastel.ui.commands;

import edu.kit.kastel.utils.RegexConstructor;

/**
 * Abstract base class for commands.
 * Provides methods for executing a command, obtaining its name,
 * and constructing a regex pattern to match command arguments.
 *
 * @author uyqbd
 */
public abstract class Command {
    /**
     * A constant representing the delimiter used to separate command arguments.
     * This separator is utilized in various commands to split or parse input stringsю
     */
    public static final String SEPARATOR = " ";

    /**
     * Executes the command with the provided arguments.
     *
     * @param args the arguments passed to the command
     * @throws CommandException if an error occurs during command execution
     */
    public abstract void execute(String[] args) throws CommandException;

    /**
     * Returns the name of the command.
     *
     * @return the command name as a String
     */
    public abstract String getName();

    /**
     * Returns a regex pattern for matching command arguments.
     * Subclasses may override this method to define specific argument formats.
     *
     * @return a regex pattern as a String, or an empty string if no arguments are expected
     */
    protected String getArgsRegex() {
        return "";
    }

    /**
     * Constructs a complete regex pattern to match the full command input.
     * The pattern is built by combining the command name and its argument pattern.
     *
     * @return a regex pattern as a String that represents the expected full input for the command
     */
    public String toRegex() {
        return "^%s$".formatted(RegexConstructor.groupAND(null,
                getArgsRegex().isEmpty() ? "" : SEPARATOR,
                getName(),
                RegexConstructor.group("", getArgsRegex()))
        );
    }

}
