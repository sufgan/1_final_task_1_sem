package edu.kit.kastel.ui.handlers;


import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.LoadCommand;
import edu.kit.kastel.ui.commands.ShowMonstersCommand;

import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.QuitCommand;

import java.util.List;

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

    @Override
    protected List<Command> getAvailableCommands() {
        return List.of(
                new LoadCommand(),
                new ShowMonstersCommand(),
                new CompetitionCreateCommand(),
                new QuitCommand()
        );
    }
}
