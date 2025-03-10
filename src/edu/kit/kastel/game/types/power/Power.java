package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.element.Element;
import edu.kit.kastel.utils.RegexConstructor;

/**
 * The Power class serves as an abstract representation of a power or effect in a game context.
 * It provides a foundation for different power implementations, each with their own behavior
 * and effects when interacting with game entities, such as monsters and elements.
 *
 * @author uyqbd
 */
public abstract class Power {
    private static final String MESSAGE_FORMAT = "%s%d";
    private static final int ARGS_COUNT = 2;
    private static final int TYPE_INDEX = 0;
    private static final int VALUE_INDEX = 1;


    private final int value;
    private final String type;

    /**
     * Constructs an instance of the Power class, representing a generic power
     * with a specified numeric value and type identifier.
     *
     * @param value the numeric value representing the magnitude or strength of the power
     * @param type  the string identifier representing the type of the power
     */
    protected Power(int value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Calculates and returns an integer value based on the provided user, target,
     * action element, and a flag indicating if it's the first calculation.
     *
     * @param user          the monster using the power; its stats or condition may influence the result
     * @param target        the target monster affected by the power; its stats or condition may influence the result
     * @param actionElement the element associated with the action, which may affect the value calculation
     * @return an integer representing the calculated value of the power for this interaction
     */
    public abstract int getValue(Monster user, Monster target, Element actionElement);

    /**
     * Retrieves the base value of the power instance.
     *
     * @return the numeric value representing the base power.
     */
    public int getValue() {
        return value;
    }

    /**
     * Creates and returns an instance of a specific subclass of Power based on the given arguments.
     * The method expects the first argument to specify the type of Power to create, and the second
     * argument to specify the numeric value associated with the Power. If the arguments are invalid
     * or do not match any supported Power type, the method returns null.
     *
     * @param args an array of Strings where the first element specifies the Power type and the
     *             second element specifies the numeric value for the Power
     * @return a specific instance of a subclass of Power (AbsolutePower, RelativePower, or BasicPower)
     *         if the arguments are valid; otherwise, returns null
     */
    public static Power create(String... args) {
        if (args.length != ARGS_COUNT) {
            return null;
        }
        int value = Integer.parseInt(args[VALUE_INDEX]);
        return switch (args[TYPE_INDEX]) {
            case AbsolutePower.NAME -> new AbsolutePower(value);
            case RelativePower.NAME -> new RelativePower(value);
            case BasicPower.NAME -> new BasicPower(value);
            default -> null;
        };
    }

    /**
     * Generates a regular expression that matches instances of different subclasses
     * of the {@code Power} class. The generated regex can optionally include a named
     * group based on the class name of {@code Power}.
     *
     * @param nameGroup a boolean indicating whether the regular expression should
     *                  include a named group for the {@code Power} class name
     * @return a {@code String} representing the regular expression that matches
     *         patterns for {@code AbsolutePower}, {@code RelativePower}, and
     *         {@code BasicPower}
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(nameGroup ? Power.class.getSimpleName() : null,
                AbsolutePower.getRegex(false),
                RelativePower.getRegex(false),
                BasicPower.getRegex(false)
        );
    }

    @Override
    public String toString() {
        return MESSAGE_FORMAT.formatted(type, value);
    }

}
