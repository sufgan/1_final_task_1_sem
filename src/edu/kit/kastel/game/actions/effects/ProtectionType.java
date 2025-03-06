package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

/**
 * Specifies protection types that can shield a monster from certain effects.
 *
 * @author uyqbd
 */
public enum ProtectionType implements RegexProvider {
    /**
     * Represents a type of protection focused on preserving or restoring health.
     * This protection type ensures that effects targeting health can be absorbed
     * or negated based on the specific implementation within the game mechanics.
     */
    HEALTH,
    /**
     * Represents a type of protection that can shield a monster by affecting its statistics.
     * This includes modifications or immunities related to attributes such as attack, defense, speed, etc.
     */
    STATS;

    /**
     * Builds a regex pattern matching any {@code ProtectionType} constant.
     *
     * @param nameGroup {@code true} to include a named group in the regex pattern
     * @return a regex pattern for matching a {@code ProtectionType}
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? ProtectionType.class.getSimpleName() : null,
                false, ProtectionType.values()
        );
    }

    /**
     * Converts a string to its corresponding {@code ProtectionType}
     * by converting the input to uppercase.
     *
     * @param regexName the string to match against a {@code ProtectionType}
     * @return the matching {@code ProtectionType} constant
     */
    public static ProtectionType valueOfRegexName(String regexName) {
        return valueOf(regexName.toUpperCase());
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
