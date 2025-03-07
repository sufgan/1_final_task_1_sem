package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.utils.RegexConstructor;
import edu.kit.kastel.utils.RegexProvider;

/**
 * Indicates whether an effect targets the user or another monster.
 *
 * @author uyqbd
 */
public enum TargetType implements RegexProvider {
    /**
     * Represents a target type for an effect within the game. This target type
     * specifies that the effect is intended to be directed at an entity
     * distinct from the user.
     */
    TARGET,
    /**
     * Represents an effect target type where the effect is applied to the user
     * who initiated it.
     */
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
