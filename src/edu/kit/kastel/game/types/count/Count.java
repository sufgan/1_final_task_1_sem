package edu.kit.kastel.game.types.count;

import edu.kit.kastel.utils.RegexConstructor;

/**
 * Base class for counting mechanics (fixed or random).
 * Provides methods to retrieve a numeric count value.
 *
 * @author uyqbd
 */
public abstract class Count {
    private static final int VALUE_INDEX = 0;
    private static final int MIN_INDEX = 1;
    private static final int MAX_INDEX = 2;

    /**
     * Creates a {@code Count} instance based on the provided arguments.
     * <ul>
     *   <li>If there is exactly one argument, a fixed value is used.</li>
     *   <li>If there are three arguments, a random range is used.</li>
     *   <li>Otherwise, {@code null} is returned.</li>
     * </ul>
     *
     * @param args the string arguments determining the type of count
     * @return a new {@code Count} instance, or {@code null} if invalid arguments
     */
    public static Count create(String... args) {
        return switch (args.length) {
            case 1 -> new ValueCount(Integer.parseInt(args[VALUE_INDEX]));
            case 3 -> new RandomCount(Integer.parseInt(args[MIN_INDEX]), Integer.parseInt(args[MAX_INDEX]));
            default -> null;
        };
    }

    /**
     * Retrieves a numeric count value based on the count implementation.
     * The provided debug message can be used for logging or troubleshooting purposes.
     *
     * @param debugMessage a string message used for debugging or additional context
     * @return the integer value of the count as determined by the specific implementation
     */
    public abstract int getValue(String debugMessage);

    /**
     * Builds a regex pattern matching either a {@link ValueCount}
     * or a {@link RandomCount}.
     *
     * @param nameGroup     {@code true} to include a named group
     * @param nameSubGroups {@code true} to include subgroups
     * @return a regex string representing valid count expressions
     */
    public static String getRegex(boolean nameGroup, boolean nameSubGroups) {
        return RegexConstructor.groupOR(nameGroup ? Count.class.getSimpleName() : null,
                ValueCount.getRegex(nameSubGroups),
                RandomCount.getRegex(nameSubGroups)
        );
    }
}
