package edu.kit.kastel.ui.commands;

import edu.kit.kastel.Application;

/**
 * Command to quit the application.
 * <p>
 * When executed, this command stops the application.
 * </p>
 *
 * @author uyqbd
 */
public class QuitCommand extends Command {
    @Override
    public void execute(String[] args) throws CommandException {
        Application.stop();
    }

    @Override
    public String getName() {
        return "quit";
    }

}
