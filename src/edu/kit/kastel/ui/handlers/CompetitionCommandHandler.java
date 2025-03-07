package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.PassCommand;
import edu.kit.kastel.ui.commands.ShowActionsCommand;
import edu.kit.kastel.ui.commands.ShowCommand;
import edu.kit.kastel.ui.commands.ActionCommand;
import edu.kit.kastel.ui.commands.QuitCommand;
import edu.kit.kastel.ui.commands.ShowStatsCommand;

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
     * Constructs a CompetitionCommandHandler that handles competition-related commands
     * during a competition by delegating functionalities to the parent CommandHandler.
     *
     * @param outerCommandHandler the parent CommandHandler handling commands outside the competition context
     * @param competition the Competition instance containing the monsters and their states to be managed
     */
    public CompetitionCommandHandler(CommandHandler outerCommandHandler, Competition competition) {
        super(outerCommandHandler);
        this.competition = competition;
    }

    @Override
    protected List<Command> getAvailableCommands() {
        return List.of(
                new ShowStatsCommand(),
                new ShowCommand(),
                new ShowActionsCommand(),
                new PassCommand(),
                new ActionCommand(),
                new CompetitionCreateCommand(),
                new QuitCommand()
        );
    }

    @Override
    protected boolean isRunning() {
        List<Monster> aliveMonsters = competition.getAliveMonsters();
        if (super.isRunning()) {
            if (aliveMonsters.size() > 1) {
                System.out.printf("%nWhat should %s do?%n", competition.getCurrentMonster().getName());
                return true;
            } else if (!aliveMonsters.isEmpty()) {
                System.out.printf("%n%s has no opponents left and wins the competition!%n", aliveMonsters.get(0).getName());
            } else {
                System.out.printf("%nAll monsters have fainted. The competition ends without a winner!%n");
            }
        }
        return false;
    }

    /**
     * Retrieves the current competition instance being managed.
     *
     * @return the {@link Competition} object representing the current competition
     */
    public Competition getCompetition() {
        return competition;
    }
}
