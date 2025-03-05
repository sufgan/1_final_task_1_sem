package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.ui.commands.CommandException;

public class ShowCommand extends CompetitionCommand {
    public ShowCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        competition.printMonsters();
    }

    @Override
    public String getName() {
        return "show";
    }

}
