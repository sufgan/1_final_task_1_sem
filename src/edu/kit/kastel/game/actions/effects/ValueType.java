package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.utils.RegexConstructor;
import edu.kit.kastel.utils.RegexProvider;

/**
 * Specifies different types of numerical values used in effects,
 * such as health, attack, defense, rate, or percentage.
 *
 * @author uyqbd
 */
public enum ValueType implements RegexProvider {
    /**
     * Represents a general numerical value type. This is a default enumeration
     * used when no specific other value type (e.g., health, attack, defense) is applicable.
     *
     * <p>
     * Commonly associated with numeric values indicating quantities, such as scores,
     * points, or similar metrics without a distinctly defined subtype.
     * </p>
     */
    VALUE,
    /**
     * Represents the minimum value in a range of numerical values used
     * in various game mechanics, such as health, attack, defense, or other statistics.
     * This constant is used to define the lower limit of a permissible range.
     */
    MIN,
    /**
     * Represents the maximum value type used in effects such as health, attack, defense, rate, or percentage.
     * This enum constant is typically associated with defining the upper limit for various numerical attributes
     * within the game mechanics.
     */
    MAX,
    /**
     * Represents the type related to health value in the game's effects system.
     * This type is used for attributes or calculations involving the health of a
     * character or entity. Typically associated with numerical values that define
     * health points in gameplay logic.
     */
    HEALTH,
    /**
     * Represents the attack value used within the context of game effects.
     * Typically associated with calculations involving combat or offensive
     * capabilities of a game entity.
     */
    ATK,
    /**
     * Represents the "defense" value type used in the game.
     * This value type is typically associated with effects that influence
     * the defensive attributes of a character or entity, such as reducing
     * or increasing defense-related stats during gameplay.
     */
    DEF,
    /**
     * Represents the speed attribute in the context of numerical values
     * used in game effects. This attribute is often utilized to determine
     * the order of actions or influence speed-based calculations.
     */
    SPD,
    /**
     * Represents a numerical value expressed as a rate.
     * Typically used to define percentages or proportions within the game's mechanics.
     *
     * <p>
     * This variable recognizes valid rate values such as integers between 0 and 100.
     * It is often used in configurations involving hit rates, effect probabilities,
     * or scaling factors.
     * </p>
     */
    RATE,
    /**
     * Represents a percentage value used in various calculations within the game.
     *
     * <p>
     * Commonly utilized for effects or computations that require ratios or probabilities,
     * such as hit rates, damage reduction, or stat modifications. Valid values typically
     * range from 0 to 100, inclusive.
     * </p>
     */
    PERCENTAGE,
    /**
     * Represents a numerical value change, positive or negative.
     * This type is used for actions or effects that modify a value
     * by adding or subtracting a specific amount.
     */
    CHANGE;

    private static final String RANGE_0_INF_REGEX = "\\d+";
    private static final String RANGE_0_INF_FLAG_REGEX = "[+-]?" + RANGE_0_INF_REGEX;
    private static final String RANGE_1_INF_REGEX = "[1-9]\\d*";
    private static final String RANGE_0_100_REGEX = RegexConstructor.groupOR(null, "0", "100", "[1-9]\\d?");


    @Override
    public String toRegex(boolean nameGroup) {
        String regex = switch (this) {
            case VALUE, MIN, MAX -> RANGE_0_INF_REGEX;
            case HEALTH, ATK, DEF, SPD -> RANGE_1_INF_REGEX;
            case RATE, PERCENTAGE -> RANGE_0_100_REGEX;
            case CHANGE -> RANGE_0_INF_FLAG_REGEX;
        };
        return nameGroup ? RegexConstructor.group(this.name(), regex) : regex;
    }

}
