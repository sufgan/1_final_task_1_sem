package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.ui.commands.CommandException;
import edu.kit.kastel.ui.commands.ShowStatsCommand;
import edu.kit.kastel.ui.commands.ActionCommand;
import edu.kit.kastel.ui.commands.PassCommand;
import edu.kit.kastel.ui.commands.ShowActionsCommand;
import edu.kit.kastel.ui.commands.ShowCommand;

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
    public void startHandling() {
        while (competition.getAliveMonsters().size() > 1) {
            try {
                System.out.printf("%nWhat should %s do?%n", competition.getCurrentMonster().getName());
                handleCommand(Application.readInputLine());
            } catch (CommandException | GameRuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
