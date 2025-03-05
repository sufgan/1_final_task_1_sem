package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

public final class RelativePower extends Power {
    public static final String NAME = "rel";

    public RelativePower(double value) {
        super(value, "r");
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        return (int) Math.ceil(target.getSample().getMaxHealth() * getValue());
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? RelativePower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.PERCENTAGE.toRegex(false)
        );
    }

}
