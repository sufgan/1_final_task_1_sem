package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

/**
 * Indicates whether an effect targets the user or another monster.
 *
 * @author uyqbd
 */
public enum TargetType implements RegexProvider {
    TARGET,
    USER;

    /**
     * Builds a regex pattern matching either {@code TARGET} or {@code USER}.
     *
     * @param nameGroup {@code true} to include a named group in the regex pattern
     * @return a regex string that matches any valid {@code TargetType}
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? TargetType.class.getSimpleName() : null,
                false, TargetType.values()
        );
    }

    /**
     * Converts a lowercase or mixed-case string into the corresponding {@code TargetType}.
     *
     * @param regexName the name (case-insensitive) of the target type
     * @return the matching {@code TargetType} constant
     */
    public static TargetType valueOfRegexName(String regexName) {
        return valueOf(regexName.toUpperCase());
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
