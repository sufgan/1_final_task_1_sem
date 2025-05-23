package edu.kit.kastel.game.types.count;

import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.utils.RegexConstructor;

/**
 * A {@code Count} implementation that returns a random integer between
 * a specified minimum and maximum value.
 *
 * @author uyqbd
 */
public final class RandomCount extends Count {
    private static final String RANDOM_LABEL = "random";

    private final int min;
    private final int max;

    /**
     * Constructs a {@code RandomCount} with the given range.
     *
     * @param min the minimum integer value (inclusive)
     * @param max the maximum integer value (inclusive)
     */
    public RandomCount(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Builds a regex pattern for matching the random count syntax.
     *
     * @param nameGroup {@code true} to include a named group in the pattern
     * @return a regex string representing the random count format
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE,
                RANDOM_LABEL,
                ValueType.MIN.toRegex(false),
                ValueType.MAX.toRegex(false)
        );
    }

    @Override
    public int getValue(String debugMessage) {
        return RandomGenerator.getRandomNumber(min, max, debugMessage);
    }

}
