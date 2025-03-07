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

    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) throws CommandException {
        handler.getCompetition().printMonsters();
    }

    @Override
    public String getName() {
        return "show";
    }

}
