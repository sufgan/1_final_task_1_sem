package edu.kit.kastel.ui.handlers;


import edu.kit.kastel.game.Competition;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.LoadCommand;
import edu.kit.kastel.ui.commands.ShowMonstersCommand;

import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.QuitCommand;

import java.util.List;
import java.util.Scanner;

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
     * Constructs a DefaultCommandHandler instance with the provided scanner for user input.
     * This command handler initializes with a predefined set of commands
     * that are applicable before starting a competition.
     *
     * @param scanner the Scanner instance used to read input from the user
     */
    public DefaultCommandHandler(Scanner scanner) {
        super(scanner);
    }

    @Override
    protected List<Command> getAvailableCommands() {
        return List.of(
                new LoadCommand(),
                new ShowMonstersCommand(),
                new CompetitionCreateCommand(),
                new QuitCommand()
        );
    }

    @Override
    public void handleCompetition(Competition competition) {
        new CompetitionCommandHandler(scanner, this, competition).startHandling();
    }

}
