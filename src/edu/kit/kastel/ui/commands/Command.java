package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ui.handlers.CommandHandler;

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
     * Executes the given command with the specified arguments.
     *
     * @param handler the CommandHandler instance that manages and executes commands
     * @param args    the array of arguments passed to the command, typically parsed from the input
     * @throws CommandException if an error occurs during the execution of the command
     *                          or the argument list does not meet the command's requirements
     */
    public abstract void execute(CommandHandler handler, String[] args) throws CommandException;

    /**
     * Returns the name of the command.
     *
     * @return the command name as a String
     */
    public abstract String getName();

    public String getArgsRegex() {
        return "$";
    }

}
