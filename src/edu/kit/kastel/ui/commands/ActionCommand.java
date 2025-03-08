package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * A command to select an action for the current monster, with an optional target.
 *
 * @author uyqbd
 */
public class ActionCommand extends CompetitionCommand {

    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) throws CommandException {
        try {
            Action action = Action.find(args[0]);
            String targetMonsterName = args.length == 2 ? args[1] : null;
            handler.getCompetition().selectAction(action, targetMonsterName);
        } catch (GameRuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "action";
    }

    @Override
    public String getArgsRegex() {
        return "\\w+(\\s\\w+(#\\d+)?)?" + super.getArgsRegex();
    }

}
