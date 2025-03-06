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
    ATK(2),
    DEF(2),
    SPD(2),
    PRC(3),
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
