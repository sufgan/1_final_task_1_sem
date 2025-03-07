package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;
import edu.kit.kastel.utils.RegexConstructor;

/**
 * A command to select an action for the current monster, with an optional target.
 *
 * @author uyqbd
 */
public class ActionCommand extends CompetitionCommand {

    /**
     * Constructs an ActionCommand for managing the selection of actions in a competition.
     *
     * @param handler the CompetitionCommandHandler responsible for handling competition-related commands
     */
    public ActionCommand(CompetitionCommandHandler handler) {
        super(handler);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        Action action = Action.find(args[0]);
        String targetMonsterName = args.length == 2 ? args[1] : null;
        try {
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
    protected String getArgsRegex() {
        return RegexConstructor.groupAND(null, "",
                RegexConstructor.groupOR(null,
                        handler.getCompetition().getCurrentMonster().getSample().getActions().toArray(new String[0])
                ),
                "(?: \\w+(?:#\\d+)?)?"
        );
    }

}
