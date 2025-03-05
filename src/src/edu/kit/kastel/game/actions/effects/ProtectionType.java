package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum ProtectionType implements RegexProvider {
    HEALTH,
    STATS;

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? ProtectionType.class.getSimpleName() : null,
                false, ProtectionType.values()
        );
    }

    public static ProtectionType valueOfRegexName(String regexName) {
        return valueOf(regexName.toUpperCase());
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return this.name().toLowerCase();
    }

}
