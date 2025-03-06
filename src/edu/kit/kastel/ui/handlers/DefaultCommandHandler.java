package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.LoadCommand;
import edu.kit.kastel.ui.commands.QuitCommand;
import edu.kit.kastel.ui.commands.ShowMonstersCommand;

/**
 * Default command handler that registers and manages the basic commands
 * available before a competition is started.
 * <p>
 * This handler includes commands to quit the application, load a configuration,
 * create a competition, and display all available monster samples.
 * </p>
 *
 * @author uyqbd
 */
public class DefaultCommandHandler extends CommandHandler {
    private boolean running;

    /**
     * Constructs a DefaultCommandHandler by registering the default commands:
     * <ul>
     *   <li>{@link QuitCommand} to terminate the application,</li>
     *   <li>{@link LoadCommand} to load a configuration file,</li>
     *   <li>{@link CompetitionCreateCommand} to start a new competition,</li>
     *   <li>{@link ShowMonstersCommand} to display all available monster samples.</li>
     * </ul>
     */
    public DefaultCommandHandler() {
        super(
                new QuitCommand(),
                new LoadCommand(),
                new CompetitionCreateCommand(),
                new ShowMonstersCommand()
        );
        running = true;
    }

    /**
     * Indicates whether the command handling process is currently active.
     *
     * @return true if the command handling is ongoing, false otherwise
     */
    protected boolean isRunning() {
        return running;
    }

    /**
     * Stops the command handling process by setting the running state to false.
     * <p>
     * This method is typically invoked to terminate the loop that processes commands,
     * effectively halting further handling until explicitly restarted, if supported.
     */
    public void stop() {
        running = false;
    }

}
