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
    private static final int ACTION_NAME_INDEX = 0;
    private static final int TARGET_NAME_INDEX = 1;
    private static final int ARGS_COUNT_WITH_TARGET = 2;


    @Override
    public void execute(CompetitionCommandHandler handler, String[] args) throws CommandException {
        try {
            Action action = Action.find(args[ACTION_NAME_INDEX]);
            String targetMonsterName = args.length == ARGS_COUNT_WITH_TARGET ? args[TARGET_NAME_INDEX] : null;
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
