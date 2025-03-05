package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

public abstract class Power {
    private final double value;
    private final String type;

    protected Power(double value, String type) {
        this.value = value;
        this.type = type;
    }

    public abstract int getValue(Monster user, Monster target, Element actionElement);

    public static Power create(String... args) {
        if (args.length != 2) {
            return null;
        }
        return switch (args[0]) {
            case AbsolutePower.NAME -> new AbsolutePower(Integer.parseInt(args[1]));
            case RelativePower.NAME -> new RelativePower(Integer.parseInt(args[1]));
            case BasicPower.NAME -> new BasicPower(Integer.parseInt(args[1]));
            default -> null;
        };
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(nameGroup ? Power.class.getSimpleName() : null,
                AbsolutePower.getRegex(false),
                RelativePower.getRegex(false),
                BasicPower.getRegex(false)
        );
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%s%d".formatted(type, (int) value);
    }
}
