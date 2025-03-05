package edu.kit.kastel.game.types.count;

import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.utils.RegexConstructor;

public final class RandomCount extends Count {
    private final static String DEBUG_MESSAGE = "duration or count of repeating";

    private final int min;
    private final int max;

    public RandomCount(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE,
                "random",
                ValueType.MIN.toRegex(false),
                ValueType.MAX.toRegex(false)
        );
    }

    @Override
    public int getValue() {
        return RandomGenerator.getRandomNumber(min, max, DEBUG_MESSAGE);
    }
}
