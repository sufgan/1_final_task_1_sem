package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ui.handlers.CommandHandler;
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

    @Override
    public void execute(CommandHandler handler, String[] args) throws CommandException {
        if (handler instanceof CompetitionCommandHandler) {
            execute((CompetitionCommandHandler) handler, args);
        } else {
            throw new CommandException("you have to start competition first");
        }
    }

    public abstract void execute(CompetitionCommandHandler handler, String[] args) throws CommandException;

}
