package edu.kit.kastel.ui.commands;

import edu.kit.kastel.game.Competition;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.ui.handlers.CommandHandler;
import edu.kit.kastel.ui.handlers.CompetitionCommandHandler;
import edu.kit.kastel.ui.handlers.DefaultCommandHandler;

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
    private static final String NAME = "competition";
    private static final String ARGS_REGEX_FORMAT = "\\w+(\\s\\w+){1,}";
    private static final String FEW_ARGS_MESSAGE = "not enough monsters to start competition";
    private static final String MONSTER_NOT_FOUND_MESSAGE = "monster %s not found";


    @Override
    public void execute(CommandHandler handler, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new CommandException(FEW_ARGS_MESSAGE);
        }
        List<MonsterSample> monsterSamples = new LinkedList<>();
        for (String arg : args) {
            MonsterSample monsterSample = MonsterSample.find(arg);
            if (monsterSample != null) {
                monsterSamples.add(monsterSample);
            } else {
                throw new CommandException(MONSTER_NOT_FOUND_MESSAGE.formatted(arg));
            }
        }
        Competition competition = new Competition(monsterSamples);
        if (handler instanceof DefaultCommandHandler) {
            new CompetitionCommandHandler(handler, competition).startHandling();
        } else {
            handler.stop(1);
            new CompetitionCommandHandler(handler.getOuterCommandHandler(), competition).startHandling();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getArgsRegex() {
        return ARGS_REGEX_FORMAT + super.getArgsRegex();
    }

}
