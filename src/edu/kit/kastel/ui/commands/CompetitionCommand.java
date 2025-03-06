package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;

/**
 * Abstract base class for competition-related commands.
 * Provides access to the current {@link Competition} instance for command execution.
 *
 * @author uyqbd
 */
public abstract class CompetitionCommand extends Command {
    protected final Competition competition;

    /**
     * Constructs a CompetitionCommand with the given competition context.
     *
     * @param competition the current competition instance
     */
    public CompetitionCommand(Competition competition) {
        this.competition = competition;
    }

}
