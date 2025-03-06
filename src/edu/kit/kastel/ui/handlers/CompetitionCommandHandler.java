package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.ui.commands.ShowStatsCommand;
import edu.kit.kastel.ui.commands.ActionCommand;
import edu.kit.kastel.ui.commands.PassCommand;
import edu.kit.kastel.ui.commands.ShowActionsCommand;
import edu.kit.kastel.ui.commands.ShowCommand;

import java.util.List;

/**
 * Handles competition-related commands during a competition.
 * <p>
 * This handler registers commands for showing monsters, displaying actions,
 * passing a turn, selecting an action, and showing statistics.
 * It continuously reads input from the application and processes commands until
 * only one monster remains in the competition.
 * </p>
 *
 * @author uyqbd
 */
public class CompetitionCommandHandler extends CommandHandler {
    private final Competition competition;

    /**
     * Constructs a CompetitionCommandHandler for the specified competition.
     *
     * @param competition the current competition instance
     */
    public CompetitionCommandHandler(Competition competition) {
        super(
                new ShowCommand(competition),
                new ShowActionsCommand(competition),
                new PassCommand(competition),
                new ActionCommand(competition),
                new ShowStatsCommand(competition)
        );
        this.competition = competition;
    }

    @Override
    protected boolean isRunning() {
        List<Monster> aliveMonsters = competition.getAliveMonsters();
        boolean running = aliveMonsters.size() > 1;
        if (running) {
            System.out.printf("%nWhat should %s do?%n", competition.getCurrentMonster().getName());
        } else {
            if (!aliveMonsters.isEmpty()) {
                System.out.printf("%n%s has no opponents left and wins the competition!%n", aliveMonsters.get(0).getName());
            } else {
                System.out.printf("%nAll monsters have fainted. The competition ends without a winner!%n");
            }
        }
        return running;
    }
}
