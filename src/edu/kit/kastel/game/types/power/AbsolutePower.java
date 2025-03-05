package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

public final class AbsolutePower extends Power {
    public static final String NAME = "abs";

    public AbsolutePower(int value) {
        super(value, "a");
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        return (int) getValue();
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? AbsolutePower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.VALUE.toRegex(false)
        );
    }

}
