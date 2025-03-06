package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.Application;
import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.CommandException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract base class for handling commands.
 * <p>
 * This class maintains a set of commands and continuously reads input from the application,
 * delegating the input to the appropriate command based on a regex match.
 * If the command is unknown or the arguments do not match the expected format, a
 * {@link CommandException} is thrown.
 * </p>
 *
 * @author uyqbd
 */
public abstract class CommandHandler {
    private static final String ERROR_UNKNOWN_COMMAND_FORMAT = "unknown command or wrong count/type of arguments: %s";

    private final Map<String, Command> commands;
    private boolean running;

    /**
     * Constructs a CommandHandler with the specified commands.
     *
     * @param commands an array of Command instances to be handled
     */
    protected CommandHandler(Command... commands) {
        this.commands = new HashMap<>();
        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }
    }

    /**
     * Starts the command handling loop.
     * <p>
     * This method continuously reads input from the application until the handler is stopped.
     * Each input line is processed using the {@link #handleCommand(String)} method.
     * </p>
     */
    public void startHandling() {
        running = true;
        while (running) {
            try {
                handleCommand(Application.readInputLine());
            } catch (CommandException | ConfigPatternException e) {
                System.err.println(e.getMessage());
            }
        }
        // TODO: add config didn't found exception
    }

    /**
     * Processes a single command line.
     * <p>
     * The method iterates over all registered commands, matching the input line against each
     * command's regex pattern. If a match is found, the command is executed with the extracted
     * arguments. If no command matches, a {@link CommandException} is thrown.
     * </p>
     *
     * @param line the input command line to handle
     * @throws CommandException if the command is unknown or the arguments are invalid
     */
    public void handleCommand(String line) throws CommandException {
        for (Command command : commands.values()) {
            Matcher matcher = Pattern.compile(command.toRegex()).matcher(line);
            if (matcher.find()) {
                String[] args = matcher.group(1).split(Command.SEPARATOR);
                command.execute(args);
                return;
            }
        }
        throw new CommandException(String.format(ERROR_UNKNOWN_COMMAND_FORMAT, line));
    }

    /**
     * Stops the command handling loop.
     */
    public void stop() {
        running = false;
    }

}
