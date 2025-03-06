package edu.kit.kastel.ui.commands;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;

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
    public void execute(String[] args) throws CommandException {
        try {
            ConfigParser.parse(args[0]);
        } catch (ConfigPatternException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    protected String getArgsRegex() {
        return "\\S+";
    }

}
