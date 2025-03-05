package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.monsters.MonsterSample;

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
