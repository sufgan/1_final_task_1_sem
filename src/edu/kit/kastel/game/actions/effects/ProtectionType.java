package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

/**
 * Specifies protection types that can shield a monster from certain effects.
 *
 * @author uyqbd
 */
public enum ProtectionType implements RegexProvider {
    HEALTH,
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
