package edu.kit.kastel.ui.commands.competition;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.ui.commands.Command;

public abstract class CompetitionCommand extends Command {
    protected final Competition competition;

    public CompetitionCommand(Competition competition) {
        this.competition = competition;
    }
}
