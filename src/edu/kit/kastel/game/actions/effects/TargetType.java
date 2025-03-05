package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum TargetType implements RegexProvider {
    TARGET,
    USER;

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? TargetType.class.getSimpleName() : null,
                false, TargetType.values()
        );
    }

    public static TargetType valueOfRegexName(String regexName) {
        return valueOf(regexName.toUpperCase());
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
