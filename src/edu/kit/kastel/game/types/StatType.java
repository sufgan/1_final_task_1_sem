package edu.kit.kastel.game.types;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

/**
 * Represents different monster stats with associated factors
 * for scaling (e.g., ATK, DEF, SPD, PRC, AGL).
 *
 * @author uyqbd
 */
public enum StatType implements RegexProvider {
    /**
     * Represents the ATK (Attack) stat type for a monster.
     * This stat determines the offensive scaling of a monster's abilities.
     * A scaling factor is associated with this stat to calculate its contribution
     * within the game's mechanics.
     */
    ATK(2),
    /**
     * Represents the defense (DEF) stat type, which is used to determine
     * scaling factors related to a character's or entity's defensive attributes.
     * This stat uses a scaling factor of 2, indicating its influence on related
     * calculations such as damage resistance.
     */
    DEF(2),
    /**
     * Represents the Speed (SPD) stat type for a monster.
     * This stat influences the turn order or actions of a monster
     * and scales based on its associated factor.
     */
    SPD(2),
    /**
     * Represents the Precision (PRC) stat type, used to define a specific attribute
     * or property of a monster within the system. Precision is scaled with a
     * factor of 3, influencing its relative impact in calculations.
     */
    PRC(3),
    /**
     * Represents the "AGL" (Agility) stat type used for defining a monster's agility,
     * typically influencing its speed or evasiveness in-game mechanics.
     *
     * Associated with a scaling factor of 3 to determine its contribution in various calculations.
     */
    AGL(3);

    private final double factor;

    StatType(double factor) {
        this.factor = factor;
    }

    /**
     * Retrieves the base factor used for scaling this stat.
     *
     * @return the scaling factor as a double
     */
    public double getFactor() {
        return factor;
    }

    /**
     * Builds a regex pattern matching any {@code StatType}.
     *
     * @param nameGroup {@code true} to include a named group in the pattern
     * @return a regex string for all possible {@code StatType} values
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? StatType.class.getSimpleName() : null,
                false, StatType.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return name();
    }

}
