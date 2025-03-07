package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

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
     * Constructs a ShowActionsCommand instance to display the available actions
     * of the current monster during the competition.
     *
     * @param handler the CompetitionCommandHandler responsible for managing commands
     *                within the competition context
     */
    public ShowActionsCommand(CompetitionCommandHandler handler) {
        super(handler);
    }

    @Override
    public void execute(String[] args) {
        System.out.printf("ACTIONS OF %s%n", handler.getCompetition().getCurrentMonster().getName());
        for (String actionName : handler.getCompetition().getCurrentMonster().getSample().getActions()) {
            System.out.println(Action.find(actionName));
        }
    }

    @Override
    public String getName() {
        return "show actions";
    }

}
