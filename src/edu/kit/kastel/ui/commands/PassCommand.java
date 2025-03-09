package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Command to pass a turn in the competition.
 * <p>
 * This command selects an action with an empty name, which is interpreted as the "pass" action.
 * When executed, it instructs the competition to skip the current monster's action.
 * </p>
 *
 * @author uyqbd
 */
public class PassCommand extends CompetitionCommand {
    private static final String NAME = "pass";


    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) throws CommandException {
        try {
            handler.getCompetition().selectAction(Action.EMPTY_ACTION, null);
        } catch (GameRuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

}
