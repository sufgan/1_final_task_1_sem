package edu.kit.kastel.ui.commands;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public void execute(String[] args) throws CommandException, ConfigPatternException {
        try {
            String config = Files.readString(Path.of(args[0]));
            ConfigParser.parse(config);
        } catch (IOException e) {
            throw new ConfigPatternException();
        }
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    protected String getArgsRegex() {
        return "\\S+"; // TODO: add regex to file
    }

}
