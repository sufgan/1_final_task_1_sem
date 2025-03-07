package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Displays the current status of all monsters in the competition.
 * <p>
 * This command prints the monster list with their health, status, and other details.
 * </p>
 *
 * @author uyqbd
 */
public class ShowCommand extends CompetitionCommand {

    /**
     * Constructs a ShowCommand that displays the current status of all monsters in the competition.
     * This command is used to print detailed information about each monster, including health
     * and status, during the competition.
     *
     * @param handler the CompetitionCommandHandler responsible for managing commands within the competition context
     */
    public ShowCommand(CompetitionCommandHandler handler) {
        super(handler);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        handler.getCompetition().printMonsters();
    }

    @Override
    public String getName() {
        return "show";
    }

}
