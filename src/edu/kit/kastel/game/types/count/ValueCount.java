package edu.kit.kastel.game.types.count;

import edu.kit.kastel.game.actions.effects.ValueType;

/**
 * A {@code Count} implementation that always returns a fixed integer value.
 *
 * @author uyqbd
 */
public final class ValueCount extends Count {
    private final int value;

    /**
     * Creates a {@code ValueCount} with the specified fixed value.
     *
     * @param value the fixed integer value
     */
    public ValueCount(int value) {
        this.value = value;
    }

    /**
     * Builds a regex pattern for matching a single integer value.
     *
     * @param nameGroup {@code true} to include a named group in the pattern
     * @return a regex string for a fixed value count
     */
    public static String getRegex(boolean nameGroup) {
        return ValueType.VALUE.toRegex(false);
    }

    @Override
    public int getValue() {
        return value;
    }
}
