package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.Application;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.CommandException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    private static final String ERROR_UNKNOWN_COMMAND_FORMAT = "unknown command %s";

    private final CommandHandler outerCommandHandler;
    private final Map<String, Command> commands;

    private boolean running;

    /**
     * Protected constructor for the {@code CommandHandler} class.
     * <p>
     * This no-argument constructor initializes a {@code CommandHandler} instance with a
     * {@code null} outer command handler. It delegates to the parameterized constructor.
     * </p>
     */
    protected CommandHandler() {
        this(null);
    }

    /**
     * Constructs a CommandHandler with the specified outer command handler.
     * This constructor initializes the internal command map and populates it
     * with commands obtained from the {@link #getAvailableCommands()} method.
     * The handler is set to a running state upon creation.
     *
     * @param outerCommandHandler the parent CommandHandler instance, or null if this is the root handler
     */
    protected CommandHandler(CommandHandler outerCommandHandler) {
        this.outerCommandHandler = outerCommandHandler;
        this.commands = new TreeMap<>(Comparator.reverseOrder());
        for (Command command : getAvailableCommands()) {
            this.commands.put(command.getName(), command);
        }
        running = true;
    }

    /**
     * Retrieves the list of commands that are available for execution with
     * the current command handler context. This method is meant to provide
     * access to all commands that can be processed at the given state.
     *
     * @return a list of {@link Command} instances representing the commands
     *         that can currently be executed by the command handler
     */
    protected abstract List<Command> getAvailableCommands();

    /**
     * Starts the command handling loop.
     * <p>
     * This method continuously reads input from the application until the handler is stopped.
     * Each input line is processed using the {@link #handleCommand(String)} method.
     * </p>
     */
    public void startHandling() {
        while (isRunning()) {
            try {
                handleCommand(Application.readInputLine());
            } catch (CommandException e) {
                System.err.println(e.getMessage());
            }
        }
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
            if (line.startsWith(command.getName())) {
                String[] args = new String[0];
                if (line.length() >= command.getName().length()) {
                    String rawArgs = line.substring(command.getName().length()).trim();
                    if (Pattern.matches(command.getArgsRegex(), rawArgs)) {
                        args = line.substring(command.getName().length() + 1).split(Command.SEPARATOR);
                    } else {
                        throw new CommandException("wrong count or types of arguments");
                    }
                }
                command.execute(this, args);
                return;
            }
        }
        throw new CommandException(String.format(ERROR_UNKNOWN_COMMAND_FORMAT, line));
    }

    /**
     * Checks whether the command handling process is currently active.
     *
     * @return true if the command handling loop is running, false otherwise
     */
    protected boolean isRunning() {
        return running;
    }

    /**
     * Stops the current command handler and optionally delegates the stop action
     * to its outer (parent) CommandHandler.
     *
     * @param level the stop level indicating the depth of the handler hierarchy to stop;
     *              if 0, only the current handler stops; if negative, all outer handlers stop recursively
     */
    public void stop(int level) {
        running = false;
        if (level != 0 && outerCommandHandler != null) {
            outerCommandHandler.stop(level - 1);
        }
    }

    /**
     * Retrieves the outer command handler associated with the current CommandHandler.
     * The outer command handler acts as the parent or context from which the current
     * handler was derived, allowing hierarchical command processing or delegation.
     *
     * @return the outer CommandHandler instance, or null if this handler does not have a parent
     */
    public CommandHandler getOuterCommandHandler() {
        return outerCommandHandler;
    }
}
