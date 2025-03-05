package edu.kit.kastel.ui;

import edu.kit.kastel.ui.commands.CompetitionCreateCommand;
import edu.kit.kastel.ui.commands.LoadCommand;
import edu.kit.kastel.ui.commands.QuitCommand;
import edu.kit.kastel.ui.commands.ShowMonstersCommand;

public class DefaultCommandHandler extends CommandHandler {
    public DefaultCommandHandler() {
        super(
                new QuitCommand(),
                new LoadCommand(),
                new CompetitionCreateCommand(),
                new ShowMonstersCommand()
        );
    }

}
