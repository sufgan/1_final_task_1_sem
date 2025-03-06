package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.actions.Action;

/**
 * Displays the actions available for the current monster in the competition.
 * <p>
 * This command prints a header with the current monster's name and lists all actions
 * that the monster can perform.
 * </p>
 *
 * @author uyqbd
 */
public class ShowActionsCommand extends CompetitionCommand {
    /**
     * Constructs a ShowActionsCommand with the given competition context.
     *
     * @param competition the current competition instance
     */
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
