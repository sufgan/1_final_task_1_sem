package edu.kit.kastel.ui.commands;

import edu.kit.kastel.ui.handlers.CommandHandler;

/**
 * Command to quit the application.
 * <p>
 * When executed, this command stops the application.
 * </p>
 *
 * @author uyqbd
 */
public class QuitCommand extends Command {
    private static final String NAME = "quit";


    @Override
    public void execute(CommandHandler handler, String[] args) throws CommandException {
        handler.stop(-1);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
