package edu.kit.kastel.ui.handlers;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.LoadCommand;
import edu.kit.kastel.ui.commands.PassCommand;
import edu.kit.kastel.ui.commands.ShowActionsCommand;
import edu.kit.kastel.ui.commands.ShowCommand;
import edu.kit.kastel.ui.commands.ActionCommand;
import edu.kit.kastel.ui.commands.QuitCommand;
import edu.kit.kastel.ui.commands.ShowMonstersCommand;
import edu.kit.kastel.ui.commands.ShowStatsCommand;

import java.util.List;
import java.util.Scanner;

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
    private static final String REQUEST_ACTION_MESSAGE_FORMAT = "%nWhat should %s do?%n";
    private static final String COMPETITION_END_MESSAGE_FORMAT = "%n%s has no opponents left and wins the competition!%n";
    private static final String COMPETITION_END_DRAW_MESSAGE = "%nAll monsters have fainted. The competition ends without a winner!%n";

    private final Competition competition;

    /**
     * Constructs a CompetitionCommandHandler which manages competition-related commands.
     * This handler is responsible for delegating commands to manage actions, showing
     * competition-related statistics, and facilitating control flow within a competition.
     *
     * @param scanner              the Scanner instance used for reading user input
     * @param outerCommandHandler  the parent CommandHandler providing a context for delegation
     *                              or null if this is a root handler
     * @param competition          the Competition object representing the context of the current competition
     */
    public CompetitionCommandHandler(Scanner scanner, CommandHandler outerCommandHandler, Competition competition) {
        super(scanner, outerCommandHandler);
        this.competition = competition;
    }

    @Override
    protected List<Command> getAvailableCommands() {
        return List.of(
                new LoadCommand(),
                new ShowStatsCommand(),
                new ShowCommand(),
                new ShowActionsCommand(),
                new ShowMonstersCommand(),
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
                Application.DEFAULT_OUTPUT_STREAM.printf(REQUEST_ACTION_MESSAGE_FORMAT, competition.getCurrentMonster().getName());
                return true;
            } else if (!aliveMonsters.isEmpty()) {
                Application.DEFAULT_OUTPUT_STREAM.printf(COMPETITION_END_MESSAGE_FORMAT, aliveMonsters.get(0).getName());
            } else {
                Application.DEFAULT_OUTPUT_STREAM.printf(COMPETITION_END_DRAW_MESSAGE);
            }
        }
        return false;
    }

    @Override
    public void handleCompetition(Competition competition) {
        stop(1);
        new CompetitionCommandHandler(scanner, getOuterCommandHandler(), competition).startHandling();
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
