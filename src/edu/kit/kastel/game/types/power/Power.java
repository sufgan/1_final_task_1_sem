package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.utils.RegexConstructor;

/**
 * The Power class serves as an abstract representation of a power or effect in a game context.
 * It provides a foundation for different power implementations, each with their own behavior
 * and effects when interacting with game entities, such as monsters and elements.
 *
 * @author uyqbd
 */
public abstract class Power {
    private final double value;
    private final String type;

    /**
     * Constructs an instance of the Power class, representing a generic power
     * with a specified numeric value and type identifier.
     *
     * @param value the numeric value representing the magnitude or strength of the power
     * @param type  the string identifier representing the type of the power
     */
    protected Power(double value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Calculates and returns the power value based on the interaction between the user, target,
     * and the specified action element. The calculation typically involves considering the monsters'
     * stats, conditions, and the element interactions.
     *
     * @param user          the monster that uses the power
     * @param target        the monster that is the target of the power
     * @param actionElement the elemental type associated with this action
     * @return an integer value representing the calculated power output
     */
    public abstract int getValue(Monster user, Monster target, Element actionElement);

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
        if (args.length != 2) {
            return null;
        }
        return switch (args[0]) {
            case AbsolutePower.NAME -> new AbsolutePower(Integer.parseInt(args[1]));
            case RelativePower.NAME -> new RelativePower(Integer.parseInt(args[1]));
            case BasicPower.NAME -> new BasicPower(Integer.parseInt(args[1]));
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

    /**
     * Retrieves the base value of the power instance.
     *
     * @return the numeric value representing the base power.
     */
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%s%d".formatted(type, (int) value);
    }

}
