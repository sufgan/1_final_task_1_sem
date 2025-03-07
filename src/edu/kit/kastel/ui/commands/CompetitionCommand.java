package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Abstract class representing a command within the context of a competition.
 * Subclasses are expected to define specific competition-related commands.
 * These commands utilize a CompetitionCommandHandler to manage the competition.
 * This class provides a foundational structure for commands that operate
 * on competition-specific logic.
 *
 * @author uyqbd
 */
public abstract class CompetitionCommand extends Command {
    protected final CompetitionCommandHandler handler;

    /**
     * Constructs a CompetitionCommand with the specified handler for managing competition-related commands.
     *
     * @param handler the CompetitionCommandHandler responsible for handling commands within the competition context
     */
    public CompetitionCommand(CompetitionCommandHandler handler) {
        this.handler = handler;
    }

}
