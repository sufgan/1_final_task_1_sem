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
    private final CommandHandler handler;

    /**
     * Constructs a QuitCommand instance that allows quitting the application.
     * This command is used to stop the application when executed.
     *
     * @param handler the command handler responsible for managing the application's commands
     */
    public QuitCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute(String[] args) throws CommandException {
        handler.stop(-1);
    }

    @Override
    public String getName() {
        return "quit";
    }

}
