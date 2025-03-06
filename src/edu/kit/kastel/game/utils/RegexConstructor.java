package edu.kit.kastel.game.utils;

/**
 * Utility class for constructing complex regex patterns in a structured way.
 * <p>
 * This class provides static methods and constants to generate segments of regex expressions,
 * including groups, alternations, and named capturing groups. It also includes predefined
 * patterns for whitespace.
 * </p>
 *
 * @author uyqbd
 */
public final class RegexConstructor {

    /**
     * Represents a single whitespace character in regex form.
     */
    public static final String REGEX_SPACE = "\\s";

    /**
     * Represents the alternation operator used in regex patterns.
     */
    public static final String REGEX_OR = "|";

    /**
     * Represents a pattern that matches one or more whitespace characters.
     */
    public static final String REGEX_MULTI_NEW_LINE = "\\s+";

    /**
     * Represents a pattern that matches one or more whitespace characters.
     * Can be used for new lines or spacing in multi-line patterns.
     */
    public static final String REGEX_NEW_LINE = "\\s+";

    /**
     * Private constructor to prevent instantiation.
     */
    private RegexConstructor() {
    }

    /**
     * Adds the prefix "end " to the given string.
     * <p>
     * For example, {@code addEndPrefix("action")} returns {@code "end action"}.
     * </p>
     *
     * @param s the string to be prefixed
     * @return a new string prefixed with "end "
     */
    public static String addEndPrefix(String s) {
        return "end %s".formatted(s);
    }

    /**
     * Creates a grouped regex expression by concatenating the given regex elements with
     * a specified delimiter (e.g., whitespace), optionally wrapping them in a named or
     * unnamed group.
     *
     * @param groupName     the name of the capturing group, or {@code null} for a non-capturing group,
     *                      or an empty string for a capturing group without a name
     * @param delimiter     the delimiter used to join the regex elements
     * @param regexElements the regex parts to join
     * @return a concatenated and grouped regex pattern
     */
    public static String groupAND(String groupName, String delimiter, String... regexElements) {
        if (groupName == null) {
            return String.join(delimiter, regexElements);
        }
        return group(groupName, String.join(delimiter, regexElements));
    }

    /**
     * Creates a grouped regex expression that matches any of the given regex elements
     * (i.e., an alternation using {@link #REGEX_OR}).
     *
     * @param groupName     the name of the capturing group, or {@code null} for a non-capturing group,
     *                      or an empty string for a capturing group without a name
     * @param regexElements the regex parts to alternate between
     * @return a grouped regex pattern using alternation
     */
    public static String groupOR(String groupName, String... regexElements) {
        return group(groupName, String.join(REGEX_OR, regexElements));
    }

    /**
     * Creates a grouped regex expression that matches any of the provided {@link RegexProvider} elements,
     * each converted to a regex string by calling {@link RegexProvider#toRegex(boolean)}.
     *
     * @param groupName    the name of the capturing group, or {@code null} for a non-capturing group,
     *                     or an empty string for a capturing group without a name
     * @param nameElements whether each element should include named groups in its pattern
     * @param regexElements an array of {@link RegexProvider} instances to include in the alternation
     * @param <T>          a type that implements {@link RegexProvider}
     * @return a grouped regex pattern using alternation over the provided elements
     */
    public static <T extends RegexProvider> String groupOR(String groupName, boolean nameElements, T... regexElements) {
        String[] processed = new String[regexElements.length];
        for (int i = 0; i < regexElements.length; i++) {
            if (regexElements[i] != null) {
                processed[i] = regexElements[i].toRegex(nameElements);
            }
        }
        return groupOR(groupName, processed);
    }

    /**
     * Wraps the given regex in a capturing or non-capturing group.
     * <ul>
     *   <li>If {@code groupName == null}, the group is non-capturing: <code>(?:...)</code>.</li>
     *   <li>If {@code groupName.isEmpty()}, the group is capturing without a name: <code>(...)</code>.</li>
     *   <li>Otherwise, a named capturing group is created: <code>(?&lt;name&gt;...)</code>.</li>
     * </ul>
     *
     * @param groupName the name of the capturing group, or {@code null} for non-capturing,
     *                  or empty for an unnamed capturing group
     * @param regex     the regex pattern to group
     * @return a grouped regex pattern
     */
    public static String group(String groupName, String regex) {
        if (groupName == null) {
            return "(?:%s)".formatted(regex);
        } else if (groupName.isEmpty()) {
            return "(%s)".formatted(regex);
        } else {
            return "(?<%s>%s)".formatted(groupName, regex);
        }
    }
}
