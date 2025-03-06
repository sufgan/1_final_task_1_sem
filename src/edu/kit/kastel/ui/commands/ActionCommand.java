package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.utils.RegexConstructor;

/**
 * A command to select an action for the current monster, with an optional target.
 *
 * @author uyqbd
 */
public class ActionCommand extends CompetitionCommand {
    /**
     * Constructs an {@code ActionCommand} with the given competition.
     *
     * @param competition the current competition context
     */
    public ActionCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        Action action = Action.find(args[0]);
        String targetMonsterName = args.length == 2 ? args[1] : null;
        try {
            competition.selectAction(action, targetMonsterName);
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
                RegexConstructor.groupOR(null, competition.getCurrentMonster().getSample().getActions().toArray(new String[0])),
                "(?: \\w+)?"
        );
    }

}
