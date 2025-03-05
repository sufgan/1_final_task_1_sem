package edu.kit.kastel.game.utils;

public final class RegexConstructor {
    public static final String REGEX_SPACE = "\\s";
    public static final String REGEX_OR = "|";
    public static final String REGEX_MULTI_NEW_LINE = "\\n+\\s*";
    public static final String REGEX_NEW_LINE = "\\n\\s*";

    private RegexConstructor() {

    }

    public static String addEndPrefix(String s) {
        return "end %s".formatted(s);
    }

    public static String groupAND(String groupName, String delimeter, String... regexElements) {
        if (groupName == null) {
            return String.join(delimeter, regexElements);
        }
        return group(groupName, String.join(delimeter, regexElements));
    }

    public static String groupOR(String groupName, String... regexElements) {
        return group(groupName, String.join(REGEX_OR, regexElements));
    }

    public static <T extends RegexProvider> String groupOR(String groupName, boolean nameElements, T... regexElements) {
        String[] processed = new String[regexElements.length];
        for (int i = 0; i < regexElements.length; i++) {
            if (regexElements[i] != null) {
                processed[i] = regexElements[i].toRegex(nameElements);
            }
        }
        return groupOR(groupName, processed);
    }

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
