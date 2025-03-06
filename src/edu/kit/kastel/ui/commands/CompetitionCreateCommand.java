package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Command to create a new competition using provided monster names.
 * <p>
 * This command takes one or more monster names as arguments, searches for the corresponding
 * {@link MonsterSample} instances, and uses them to instantiate a new {@link Competition}.
 * </p>
 *
 * @author uyqbd
 */
public class CompetitionCreateCommand extends Command {

    @Override
    public void execute(String[] args) throws CommandException {
        List<MonsterSample> monsterSamples = new LinkedList<>();
        for (String arg : args) {
            MonsterSample monsterSample = MonsterSample.find(arg);
            if (monsterSample != null) {
                monsterSamples.add(monsterSample);
            } else {
                System.err.printf("Monster with name %s doesn't exist%n", arg);
            }
        }
        Competition competition = new Competition(monsterSamples);
        new CompetitionCommandHandler(competition).startHandling();
    }

    @Override
    public String getName() {
        return "competition";
    }

    @Override
    protected String getArgsRegex() {
        return "\\w+(?:%s\\w+)+".formatted(SEPARATOR);
    }

}
