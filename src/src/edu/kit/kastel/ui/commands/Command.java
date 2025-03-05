package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.List;

public abstract class Command {
    public static final String SEPARATOR = " ";
//    private final static int DEFAULT_TYPE = 0x01;
//    private final static int COMPETITION_TYPE = 0x10;
//
//    private final int commandType;

    public abstract void execute(String[] args) throws CommandException;

    public abstract String getName();

    protected String getArgsRegex() {
        return "";
    }

    // TODO: remake or delete groups
    public String toRegex() {
        return "^%s$".formatted(RegexConstructor.groupAND(null,
                getArgsRegex().isEmpty() ? "" : SEPARATOR,
                getName(),
                RegexConstructor.group("", getArgsRegex()))
        );
    }

}
