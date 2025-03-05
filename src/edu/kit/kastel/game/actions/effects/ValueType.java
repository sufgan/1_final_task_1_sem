package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum ValueType implements RegexProvider {
    VALUE,
    MIN,
    MAX,
    HEALTH,
//    STATS,
    ATK,
    DEF,
    SPD,
    RATE,
    PERCENTAGE,
    CHANGE;

    // TODO
    @Override
    public String toRegex(boolean nameGroup) {
        String regex = switch (this) {
            case VALUE, MIN, MAX -> RegexConstructor.groupOR( // [0, inf)
                    null,
                    "0",
                    "[1-9]\\d*"
            ) ;
            case HEALTH, ATK, DEF, SPD -> "[1-9]\\d*"; // [1, inf)
            case RATE, PERCENTAGE -> RegexConstructor.groupOR( // [0, 100]
                    null,
                    "0", "100", "[1-9]\\d?"
            );
            case CHANGE -> "[+-]\\d+"; // TODO: check working props
        };
        if (nameGroup) {
            return RegexConstructor.group(this.name(), regex);
        }
        return regex;
    }

}
