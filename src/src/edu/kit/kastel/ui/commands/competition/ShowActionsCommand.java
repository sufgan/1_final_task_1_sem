package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.actions.Action;

public class ShowActionsCommand extends CompetitionCommand {
    public ShowActionsCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) {
        System.out.printf("ACTIONS OF %s%n", competition.getCurrentMonster().getName());
        for (String actionName : competition.getCurrentMonster().getSample().getActions()) {
            System.out.println(Action.find(actionName));
        }
    }

    @Override
    public String getName() {
        return "show actions";
    }

}
