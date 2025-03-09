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
    private static final String NAME = "action";
    private static final String ARGS_REGEX_FORMAT = "\\w+(\\s\\w+(#\\d+)?)?";

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
        return NAME;
    }

    @Override
    public String getArgsRegex() {
        return ARGS_REGEX_FORMAT + super.getArgsRegex();
    }

}
