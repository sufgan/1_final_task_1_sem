package edu.kit.kastel.ui.commands;

import edu.kit.kastel.Application;

public class QuitCommand extends Command {
    public void execute(String[] args) throws CommandException {
        Application.stop();
    }

    @Override
    public String getName() {
        return "quit";
    }

}
