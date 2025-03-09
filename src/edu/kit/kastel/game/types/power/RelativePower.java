package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.element.Element;
import edu.kit.kastel.utils.RegexConstructor;

/**
 * The RelativePower class represents a type of power in the game that calculates its
 * value relative to the maximum health of the target. It extends the abstract Power
 * class and provides specific behavior for computing the power output based on the
 * target's maximum health and a given percentage value.
 * <p>
 * This class is immutable and final, ensuring that its behavior cannot be altered
 * by subclassing.
 * </p>
 *
 * @author uyqbd
 */
public final class RelativePower extends Power {
    /**
     * Represents name for type of the power.
     */
    public static final String NAME = "rel";

    /**
     * Constructs a RelativePower instance with the specified value.
     *
     * @param value the percentage value used to compute the power relative to
     *              the target's maximum health
     */
    public RelativePower(int value) {
        super(value, NAME.substring(0, 1));
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        return (int) Math.ceil(target.getSample().getMaxHealth() * getValue() / 100.);
    }

    /**
     * Generates a regular expression that matches the {@code RelativePower}
     * pattern. The regex can optionally include a named group for the class name.
     *
     * @param nameGroup a boolean indicating whether the regular expression should
     *                  include a named group for the {@code RelativePower} class name
     * @return a {@code String} representing the regular expression for matching
     *         patterns of {@code RelativePower}
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? RelativePower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.PERCENTAGE.toRegex(false)
        );
    }

}
