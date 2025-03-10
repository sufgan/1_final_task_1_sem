package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.CommandException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
    private static final String WRONG_ARGS = "wrong count or types of arguments";

    protected final Scanner scanner;
    private final CommandHandler outerCommandHandler;
    private final Map<String, Command> commands;

    private boolean running;

    /**
     * Constructs a CommandHandler using the specified Scanner for user input.
     * This constructor initializes the handler without an outer parent, assuming
     * it is a standalone root command handler.
     *
     * @param scanner the Scanner instance used to read input from the user
     */
    protected CommandHandler(Scanner scanner) {
        this(scanner, null);
    }

    /**
     * Constructs a CommandHandler using the specified Scanner for user input
     * and an optional outer CommandHandler for hierarchical delegation.
     * This constructor initializes the handler with the commands available
     * in the current context.
     *
     * @param scanner              the Scanner instance used to read input from the user
     * @param outerCommandHandler  the parent CommandHandler providing a context for delegation,
     *                             or null if this is the root handler
     */
    protected CommandHandler(Scanner scanner, CommandHandler outerCommandHandler) {
        this.scanner = scanner;
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
     * Starts handling user input by continuously reading commands from the provided scanner.
     * The method remains active while the command handler is running, processing each
     * input line by passing it to the {@code handleCommand} method. If a {@code CommandException}
     * is thrown during the handling of a command, the error message is printed to the standard error stream.
     */
    public void startHandling() {
        while (isRunning()) {
            try {
                handleCommand(scanner.nextLine());
            } catch (CommandException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void handleCommand(String line) throws CommandException {
        for (Command command : commands.values()) {
            if (!line.startsWith(command.getName())) {
                continue;
            }
            String rawArgs = line.substring(command.getName().length()).trim();
            String[] args = parseArgs(command, rawArgs);
            command.execute(this, args);
            return;
        }
        throw new CommandException(String.format(ERROR_UNKNOWN_COMMAND_FORMAT, line));
    }

    /**
     * Handles a competition among multiple monsters within the context of the provided {@link Competition}.
     * This abstract method is designed to be implemented with specific logic for managing
     * the phases or actions of the competition.
     *
     * @param competition the {@link Competition} instance representing the competition to be handled
     */
    public abstract void handleCompetition(Competition competition);

    private String[] parseArgs(Command command, String rawArgs) throws CommandException {
        if (rawArgs.isEmpty()) {
            return new String[0];
        } else if (!Pattern.matches(command.getArgsRegex(), rawArgs)) {
            throw new CommandException(WRONG_ARGS);
        }
        return rawArgs.split(Command.SEPARATOR);
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
        if (level != 0) {
            running = false;
            if (outerCommandHandler != null) {
                outerCommandHandler.stop(level - 1);
            }
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
