package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Command to display the current statistics of the active monster in the competition.
 * <p>
 * This command prints the name and the current status (health, stats, etc.) of the monster
 * whose turn it is.
 * </p>
 *
 * @author uyqbd
 */
public class ShowStatsCommand extends CompetitionCommand {

    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) throws CommandException {
        Monster currentMonster = handler.getCompetition().getCurrentMonster();
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
