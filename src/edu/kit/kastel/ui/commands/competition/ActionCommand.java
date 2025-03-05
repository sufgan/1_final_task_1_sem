package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.ui.commands.CommandException;


public class ActionCommand extends CompetitionCommand {
    public ActionCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        Action action = Action.find(args[0]);
        String targetMonsterName = args.length == 2 ? args[1] : null;
        competition.selectAction(action, targetMonsterName);
    }

    @Override
    public String getName() {
        return "action";
    }

    @Override
    protected String getArgsRegex() {
        return RegexConstructor.groupAND(null, "",
                RegexConstructor.groupOR(null, competition.getCurrentMonster().getSample().getActions().toArray(String[]::new)),
                "(?: \\w+)?"
        );
    }

}
