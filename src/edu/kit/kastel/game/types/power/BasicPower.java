package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.utils.RegexConstructor;

/**
 * Represents a basic implementation of the Power class with specific
 * behavior for damage calculation. The BasicPower takes into account
 * various factors such as elemental efficiency, attack and defense stats,
 * critical hit probability, and random variation to compute its value.
 * <p>
 * This class is a concrete implementation of the abstract Power class and
 * provides its unique calculation for determining its effect in a game
 * context.
 * </p>
 *
 * @author uyqbd
 */
public final class BasicPower extends Power {
    private static final String CRITICAl_HIT_MESSAGE = "Critical hit!";
    private static final String CRITICAl_HIT_DEBUG_MESSAGE = "critical hit";
    private static final String RANDOM_FACTOR_DEBUG_MESSAGE = "random factor";

    public static final String NAME = "base";

    /**
     * Constructs an instance of the BasicPower class with a specified value.
     *
     * @param value the numeric value representing the base power of this BasicPower instance
     */
    public BasicPower(int value) {
        super(value, "b");
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        double elementFactor = actionElement.getEfficiency(target.getSample().getElement()).getDamageScale();
        double statusFactor = user.getStat(StatType.ATK) / target.getStat(StatType.DEF);
        double criticalHitProbability = Math.pow(10, -target.getStat(StatType.SPD) / user.getStat(StatType.SPD)) * 100;
        int criticalHitFactor;
        if (RandomGenerator.probabilityGood(criticalHitProbability, CRITICAl_HIT_DEBUG_MESSAGE)) {
            System.out.println(CRITICAl_HIT_MESSAGE); // 8
            criticalHitFactor = 2;
        } else {
            criticalHitFactor = 1;
        }
        double sameElementFactor = user.getSample().getElement() == actionElement ? 1.5 : 1;
        double randomFactor = RandomGenerator.getRandomFactor(0.85, 1, RANDOM_FACTOR_DEBUG_MESSAGE);
        double normalFactor = 1 / 3.;
        return (int) Math.ceil(getValue() * elementFactor * statusFactor * criticalHitFactor * sameElementFactor * randomFactor * normalFactor);
    }


    /**
     * Constructs a regular expression pattern based on specified parameters.
     * The resulting regular expression can include a named capturing group
     * if the `nameGroup` parameter is true.
     *
     * @param nameGroup a boolean indicating if the resulting regex pattern
     *                  should include a named capturing group associated with
     *                  the class name of `BasicPower`.
     * @return a string representing the constructed regular expression pattern
     *         which combines the BasicPower name (optional), a whitespace regex,
     *         a predefined name constant, and a regex for a numerical value.
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? BasicPower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.VALUE.toRegex(false)
        );
    }

}
