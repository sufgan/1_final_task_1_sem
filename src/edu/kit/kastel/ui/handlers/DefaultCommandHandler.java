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
    }

}
