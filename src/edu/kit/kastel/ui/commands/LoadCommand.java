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
    private static final String NAME = "load";
    private static final String ARGS_REGEX_FORMAT = "\\S+";
    private static final String WRONG_ARGS_COUNT = "wrong number of arguments";
    private static final int ARGS_COUNT = 1;
    private static final int CONFIG_PATH_INDEX = 0;


    @Override
    public void execute(CommandHandler handler, String[] args) throws CommandException {
        if (args.length != ARGS_COUNT) {
            throw new CommandException(WRONG_ARGS_COUNT);
        }
        try {
            ConfigParser.parse(args[CONFIG_PATH_INDEX]);
            if (handler instanceof CompetitionCommandHandler) {
                handler.stop(1);
            }
        } catch (ConfigPatternException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getArgsRegex() {
        return ARGS_REGEX_FORMAT + super.getArgsRegex();
    }
}
