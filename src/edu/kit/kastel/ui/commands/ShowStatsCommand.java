package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;

/**
 * Command to display the current statistics of the active monster in the competition.
 * <p>
 * This command prints the name and the current status (health, stats, etc.) of the monster
 * whose turn it is.
 * </p>
 *
 * @author uyqbd
 */
public class ShowStatsCommand extends CompetitionCommand{
    /**
     * Constructs a new ShowStatsCommand with the given competition context.
     *
     * @param competition the competition instance providing the current monster
     */
    public ShowStatsCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        Monster currentMonster = competition.getCurrentMonster();
        System.out.printf("STATS OF %s%n%s%n",
                currentMonster.getName(),
                currentMonster.toString()
        );
    }

    @Override
    public String getName() {
        return "show stats";
    }

}
