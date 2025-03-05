package edu.kit.kastel.ui.commands;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
