package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.types.Element;

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
     * Constructs a PassCommand for the given competition.
     * <p>
     * A new Action with an empty name, NORMAL element, and no effects is created
     * to represent the pass action.
     * </p>
     *
     * @param competition the current competition instance
     */
    public PassCommand(Competition competition) {
        super(competition);
        new Action("", Element.NORMAL, List.of());
    }

    @Override
    public void execute(String[] args) throws CommandException {
        try {
            competition.selectAction(Action.find(""));
        } catch (GameRuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "pass";
    }

}
