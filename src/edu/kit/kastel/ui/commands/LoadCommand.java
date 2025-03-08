package edu.kit.kastel.ui.commands;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;
import edu.kit.kastel.ui.handlers.CommandHandler;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

/**
 * Command to load a configuration file and parse it using the ConfigParser.
 * <p>
 * This command reads a configuration file from a specified path and processes it.
 * If the file cannot be read, a ConfigPatternException is thrown.
 * </p>
 *
 * @author uyqbd
 */
public class LoadCommand extends Command {
    @Override
    public void execute(CommandHandler handler, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("wrong number of arguments");
        }
        try {
            ConfigParser.parse(args[0]);
            if (handler instanceof CompetitionCommandHandler) {
                handler.stop(1);
            }
        } catch (ConfigPatternException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getArgsRegex() {
        return "\\S+" + super.getArgsRegex();
    }
}
