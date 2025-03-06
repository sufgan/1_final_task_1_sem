package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

/**
 * The AbsolutePower class represents a type of power characterized by its absolute value effect.
 * It is a subclass of the Power class and provides specific implementation for the getValue method.
 * The AbsolutePower is identified by a predefined name and value during initialization.
 *
 * @author uyqbd
 */
public final class AbsolutePower extends Power {
    public static final String NAME = "abs";

    public AbsolutePower(int value) {
        super(value, "a");
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        return (int) getValue();
    }

    /**
     * Generates a regular expression for matching patterns related to AbsolutePower.
     * It combines components such as the name of the power, a space delimiter, and
     * the value range based on the ValueType enum. The expression can optionally include
     * a named group for the power's class name.
     *
     * @param nameGroup a boolean indicating whether the generated regular expression
     *                  should include a named group for the class name
     * @return a String representing the regular expression that matches
     *         the pattern for AbsolutePower
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? AbsolutePower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.VALUE.toRegex(false)
        );
    }

}
