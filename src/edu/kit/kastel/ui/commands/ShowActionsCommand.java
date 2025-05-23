package edu.kit.kastel.ui.commands;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Displays the actions available for the current monster in the competition.
 * <p>
 * This command prints a header with the current monster's name and lists all actions
 * that the monster can perform.
 * </p>
 *
 * @author uyqbd
 */
public class ShowActionsCommand extends CompetitionCommand {
    private static final String NAME = "show actions";
    private static final String LABEL_FORMAT = "ACTIONS OF %s%n";


    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) {
        Application.DEFAULT_OUTPUT_STREAM.printf(LABEL_FORMAT, handler.getCompetition().getCurrentMonster().getName());
        for (String actionName : handler.getCompetition().getCurrentMonster().getSample().getActions()) {
            try {
                Application.DEFAULT_OUTPUT_STREAM.println(Action.find(actionName));
            } catch (GameRuntimeException e) {
                Application.DEFAULT_ERROR_STREAM.println(e.getMessage());
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

}
