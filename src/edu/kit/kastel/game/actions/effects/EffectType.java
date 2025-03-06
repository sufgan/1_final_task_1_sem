package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.types.count.Count;
import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Defines the various types of effects that can appear in the game.
 * <p>Each enum constant has an associated regex name used for matching in configuration.</p>
 *
 * @author uyqbd
 */
public enum EffectType implements RegexProvider {
    DAMAGE("damage"),
    INFLICT_STATUS_CONDITION("inflictStatusCondition"),
    INFLICT_STAT_CHANGE("inflictStatChange"),
    PROTECT_STAT("protectStat"),
    HEAL("heal"),
    REPEAT("repeat"),
    CONTINUE("continue"),;

    private final String regexName;

    /**
     * Constructs an {@code EffectType} with the specified regex name.
     *
     * @param regexName the string used to identify this effect type in regex patterns
     */
    EffectType(String regexName) {
        this.regexName = regexName;
    }

    @Override
    public String toRegex(boolean nameSubGroups) {
        return switch (this) {
            case DAMAGE, HEAL -> toDefaultRegex(nameSubGroups,
                    TargetType.getRegex(nameSubGroups),
                    Power.getRegex(nameSubGroups));
            case INFLICT_STATUS_CONDITION -> toDefaultRegex(nameSubGroups,
                    TargetType.getRegex(nameSubGroups),
                    Condition.getRegex(nameSubGroups));
            case INFLICT_STAT_CHANGE -> toDefaultRegex(nameSubGroups,
                    TargetType.getRegex(nameSubGroups),
                    StatType.getRegex(nameSubGroups),
                    ValueType.CHANGE.toRegex(nameSubGroups));
            case PROTECT_STAT -> toDefaultRegex(nameSubGroups,
                    ProtectionType.getRegex(nameSubGroups),
                    Count.getRegex(nameSubGroups, false));
            case CONTINUE -> toDefaultRegex(nameSubGroups);
            case REPEAT -> RegexConstructor.groupAND(nameSubGroups ? RepeatEffect.class.getSimpleName() : null, "",
                    regexName,
                    RegexConstructor.REGEX_SPACE,
                    Count.getRegex(nameSubGroups, false),
                    RegexConstructor.REGEX_NEW_LINE,
                    RegexConstructor.groupAND(nameSubGroups ? EffectType.class.getSimpleName() : null, "",
                            RegexConstructor.group(null, EffectType.getRegex(false, REPEAT)),
                            "+"
                    ),
                    RegexConstructor.REGEX_SPACE,
                    RegexConstructor.addEndPrefix(regexName)
            );
        };
    }

    /**
     * Builds a default regex pattern string for this effect type.
     *
     * @param nameSubGroups whether named groups should be included
     * @param components    additional regex components required for this effect type
     * @return a combined regex pattern incorporating all provided parts
     */
    private String toDefaultRegex(boolean nameSubGroups, String... components) {
        String[] fullComponents = new String[components.length + 2];
        fullComponents[0] = regexName;
        fullComponents[fullComponents.length - 1] = ValueType.RATE.toRegex(nameSubGroups);
        System.arraycopy(components, 0, fullComponents, 1, components.length);
        return RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE, fullComponents);
    }

    /**
     * Returns an {@code EffectType} whose {@code regexName} matches the given string.
     *
     * @param regexName the string to match against each enum constant's regex name
     * @return the matching {@code EffectType}, or {@code null} if none matches
     */
    public static EffectType valueOfRegexName(String regexName) {
        for (EffectType type : EffectType.values()) {
            if (type.regexName.equals(regexName)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Builds a combined regex pattern for all effect types, optionally excluding some.
     *
     * @param nameGroup      whether to include a named group for the overall pattern
     * @param excludeEffects the effect types to exclude from the pattern
     * @return a regex pattern that matches any of the included effect types
     */
    public static String getRegex(boolean nameGroup, EffectType... excludeEffects) {
        List<EffectType> effectTypes = new LinkedList<>(List.of(values()));
        effectTypes.removeAll(Set.of(excludeEffects));
        return RegexConstructor.groupAND(nameGroup ? EffectType.class.getSimpleName() : null, "",
                RegexConstructor.groupOR(null, false, effectTypes.toArray(new EffectType[0])),
                RegexConstructor.REGEX_NEW_LINE
        );
    }

}