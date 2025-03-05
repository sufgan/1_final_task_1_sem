package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.ui.commands.CommandException;

public class ShowStatsCommand extends CompetitionCommand{
    public ShowStatsCommand(Competition competition) {
        super(competition);
    }

    @Override
    public void execute(String[] args) throws CommandException {
        Monster currentMonster = competition.getCurrentMonster();
        System.out.printf("STATS OF %s%n%s%n",
                currentMonster.getName(),
                currentMonster.toString()
        );
    }

    @Override
    public String getName() {
        return "show stats";
    }

}
