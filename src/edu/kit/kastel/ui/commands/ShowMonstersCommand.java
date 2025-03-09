package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.ui.handlers.CommandHandler;

/**
 * Command to display all available monster samples.
 * <p>
 * This command prints a list of all registered monster samples by invoking
 * the static method {@link MonsterSample#allToString()}.
 * </p>
 *
 * @author uyqbd
 */
public class ShowMonstersCommand extends Command {
    private static final String NAME = "show monsters";

    @Override
    public void execute(CommandHandler handler, String[] args) {
        System.out.println(MonsterSample.allToString());
    }

    @Override
    public String getName() {
        return NAME;
    }

}
