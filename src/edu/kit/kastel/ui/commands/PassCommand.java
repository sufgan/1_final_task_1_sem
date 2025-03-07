package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

import java.util.List;

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

    /**
     * Constructs a PassCommand that skips the current monster's action during a competition.
     * The associated action is predefined as a "pass" action with no additional effects or behavior.
     *
     * @param handler the CompetitionCommandHandler responsible for managing commands within the competition context
     */
    public PassCommand(CompetitionCommandHandler handler) {
        super(handler);
        new Action("", Element.NORMAL, List.of());
    }

    @Override
    public void execute(String[] args) throws CommandException {
        try {
            handler.getCompetition().selectAction(Action.find(""));
        } catch (GameRuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "pass";
    }

}
