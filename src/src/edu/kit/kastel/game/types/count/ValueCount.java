package edu.kit.kastel.game.types.count;

import edu.kit.kastel.game.actions.effects.ValueType;

public final class ValueCount extends Count {
    private final int value;

    public ValueCount(int value) {
        this.value = value;
    }

    public static String getRegex(boolean nameGroup) {
        return ValueType.VALUE.toRegex(false);
    }

    @Override
    public int getValue() {
        return value;
    }
}
