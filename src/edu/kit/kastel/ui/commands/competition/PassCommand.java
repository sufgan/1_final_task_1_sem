package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.ui.commands.CommandException;

import java.util.List;

public class PassCommand extends CompetitionCommand {
    public PassCommand(Competition competition) {
        super(competition);
        new Action("", Element.NORMAL, List.of());
    }

    @Override
    public void execute(String[] args) throws CommandException {
        competition.selectAction(Action.find(""));
    }

    @Override
    public String getName() {
        return "pass";
    }

}
