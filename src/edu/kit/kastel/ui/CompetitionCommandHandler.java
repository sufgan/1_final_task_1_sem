package edu.kit.kastel.ui;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.ui.commands.CommandException;
import edu.kit.kastel.ui.commands.competition.*;

public class CompetitionCommandHandler extends CommandHandler {
    private final Competition competition;

    public CompetitionCommandHandler(Competition competition) {
        super(
                new ShowCommand(competition),
                new ShowActionsCommand(competition),
                new PassCommand(competition),
                new ActionCommand(competition),
                new ShowStatsCommand(competition)
        );
        this.competition = competition;
    }

    @Override
    public void startHandling() {
        while (competition.getAliveMonsters().size() > 1) {
            try {
                System.out.printf("%nWhat should %s do?%n", competition.getCurrentMonster().getName());
                handleCommand(Application.readInputLine());
            } catch (CommandException | GameRuntimeException e) {
                System.err.println(e.getMessage());
//                e.printStackTrace();
            }
        }
    }

}
