package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;

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
     * Constructs a ShowCommand with the given competition context.
     *
     * @param competition the current competition instance
     */
    public ShowCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        competition.printMonsters();
    }

    @Override
    public String getName() {
        return "show";
    }

}
