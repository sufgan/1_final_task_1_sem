package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Command to create a new competition using provided monster names.
 * <p>
 * This command takes one or more monster names as arguments, searches for the corresponding
 * {@link MonsterSample} instances, and uses them to instantiate a new {@link Competition}.
 * It then starts the competition command handler. After the competition concludes,
 * it displays the winner or indicates that all monsters have fainted.
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

        List<Monster> aliveMonsters = competition.getAliveMonsters();
        if (!aliveMonsters.isEmpty()) {
            System.out.printf("%n%s has no opponents left and wins the competition!%n", aliveMonsters.get(0).getName());
        } else {
            System.out.printf("%nAll monsters have fainted. The competition ends without a winner!%n");
        }
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
