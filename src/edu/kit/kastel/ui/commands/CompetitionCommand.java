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
        execute((CompetitionCommandHandler) handler, args);
    }

    /**
     * Executes the specific logic associated with a competition command using the provided handler
     * and arguments. This method is intended to be implemented by concrete subclasses to define
     * the functionality of a competition-related command.
     *
     * @param handler the CompetitionCommandHandler instance that manages competition-specific commands
     * @param args    the array of command arguments, typically parsed from the input
     * @throws CommandException if an error occurs during the execution of the command
     */
    public abstract void execute(CompetitionCommandHandler handler, String[] args) throws CommandException;

}
