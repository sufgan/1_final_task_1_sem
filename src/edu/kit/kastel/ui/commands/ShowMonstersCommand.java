package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.monsters.MonsterSample;

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
    @Override
    public void execute(String[] args) {
        System.out.println(MonsterSample.allToString());
    }

    @Override
    public String getName() {
        return "show monsters";
    }

}
