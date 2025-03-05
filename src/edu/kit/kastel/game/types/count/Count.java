package edu.kit.kastel.game.types.count;

import edu.kit.kastel.game.utils.RegexConstructor;

public abstract class Count {
    public abstract int getValue();

    public static Count create(String... args) {
        return switch (args.length) {
            case 1 -> new ValueCount(Integer.parseInt(args[0]));
            case 3 -> new RandomCount(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            default -> null;
        };
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupOR(nameGroup ? Count.class.getSimpleName() : null,
                ValueCount.getRegex(nameSubGroups),
                RandomCount.getRegex(nameSubGroups)
        );
    }
}
