package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

/**
 * Specifies different types of numerical values used in effects,
 * such as health, attack, defense, rate, or percentage.
 *
 * @author uyqbd
 */
public enum ValueType implements RegexProvider {
    VALUE,
    MIN,
    MAX,
    HEALTH,
    ATK,
    DEF,
    SPD,
    RATE,
    PERCENTAGE,
    CHANGE;

    @Override
    public String toRegex(boolean nameGroup) {
        String regex = switch (this) {
            case VALUE, MIN, MAX -> RegexConstructor.groupOR(null,
                    "0",
                    "[1-9]\\d*"
            ) ;
            case HEALTH, ATK, DEF, SPD -> "[1-9]\\d*";
            case RATE, PERCENTAGE -> RegexConstructor.groupOR(null,
                    "0", "100", "[1-9]\\d?"
            );
            case CHANGE -> "[+-]\\d+";
        };
        return nameGroup ? RegexConstructor.group(this.name(), regex) : regex;
    }

}
