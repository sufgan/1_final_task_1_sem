package edu.kit.kastel.config;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.actions.effects.Effect;
import edu.kit.kastel.game.actions.effects.EffectType;
import edu.kit.kastel.game.actions.effects.TargetType;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.DamageEffect;
import edu.kit.kastel.game.actions.effects.ContinueEffect;
import edu.kit.kastel.game.actions.effects.HealEffect;
import edu.kit.kastel.game.actions.effects.ProtectEffect;
import edu.kit.kastel.game.actions.effects.StatusConditionEffect;
import edu.kit.kastel.game.actions.effects.ProtectionType;
import edu.kit.kastel.game.actions.effects.RepeatEffect;
import edu.kit.kastel.game.actions.effects.StatScaleEffect;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.game.types.count.Count;
import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.types.element.Element;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.utils.RegexConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code ConfigParser} class is responsible for parsing configuration data
 * to load and create {@link Action} and {@link MonsterSample} objects based on
 * a string input. It relies on various regex patterns defined in external classes
 * to structure the config correctly.
 *
 * <p>
 * The class primarily uses the regex patterns provided by {@link Action} and
 * {@link MonsterSample} to detect and parse valid actions and monster definitions.
 * If the input does not match these patterns, it throws a {@link ConfigPatternException}.
 * </p>
 *
 * @author uyqbd
 */
public final class ConfigParser {
    private static final String CONFIG_NOT_FOUND = "config file not found";
    private static final String INVALID_CONFIG = "invalid config format";
    private static final String CONFIG_LOADED_FORMAT = "%nLoaded %d actions, %d monsters.%n";
    private static final String ACTION_NOT_FOUND_FORMAT = "action %s not found";
    private static final String DUPLICATING_NAME_FORMAT = "duplicating %s name %s";
    private static final String GROUP_NAME = "name";
    private static final String GROUP_ACTIONS = "actions";


    private ConfigParser() {

    }

    /**
     * Parses the given config string, extracting and creating {@link Action} and
     * {@link MonsterSample} instances.
     * <ul>
     *   <li>Clears all existing samples in {@link MonsterSample}</li>
     *   <li>Clears all existing actions in {@link Action}</li>
     *   <li>Parses actions, then monsters from the config</li>
     * </ul>
     *
     * @param configPath the string containing the path to configuration data.
     * @throws ConfigPatternException if the config string does not match the expected pattern.
     */
    public static void parse(String configPath) throws ConfigPatternException {
        String config;
        try {
            String preConfig = Files.readString(Path.of(configPath));
            config = preConfig + (preConfig.endsWith("\n") ? "" : "\n");
            Application.DEFAULT_OUTPUT_STREAM.print(config);
        } catch (IOException e) {
            throw new ConfigPatternException(CONFIG_NOT_FOUND);
        }
        if (!Pattern.matches(getRegex(), config)) {
            throw new ConfigPatternException(INVALID_CONFIG);
        }

        MonsterSample.clearSamples();
        Action.clearActions();

        int loadedActionsCount = parseActions(config);
        int loadedMonstersCount = parseMonsters(config);
        Application.DEFAULT_OUTPUT_STREAM.printf(CONFIG_LOADED_FORMAT, loadedActionsCount, loadedMonstersCount);
    }

    private static int parseActions(String config) throws ConfigPatternException {
        Matcher matcher = Pattern.compile(Action.getRegex(true)).matcher(config);
        int count = 0;
        Set<String> names = new HashSet<>();
        while (matcher.find()) {
            String name = matcher.group(GROUP_NAME);
            if (names.contains(name)) {
                throw new ConfigPatternException(DUPLICATING_NAME_FORMAT.formatted("action", name));
            } else {
                Element element = Element.valueOf(matcher.group(Element.class.getSimpleName()));
                new Action(name, element, parseEffects(matcher.group(EffectType.class.getSimpleName()), element));
                count++;
                names.add(name);
            }
        }
        return count;
    }

    private static List<Effect> parseEffects(String rawEffects, Element actionElement) {
        List<Effect> effectList = new LinkedList<>();
        Matcher matcher = Pattern.compile(EffectType.getRegex(false)).matcher(rawEffects);
        while (matcher.find()) {
            effectList.add(parseEffect(matcher.group(), actionElement));
        }
        return effectList;
    }

    private static List<ApplyableEffect> parseApplyableEffect(String rawEffects, Element actionElement) {
        List<ApplyableEffect> applyableEffectList = new LinkedList<>();
        for (Effect effect : parseEffects(rawEffects, actionElement)) {
            ApplyableEffect applyableEffect = effect.asApplyableEffect();
            if (applyableEffect != null) {
                applyableEffectList.add(applyableEffect);
            }
        }
        return applyableEffectList;
    }

    private static Effect parseEffect(String rawEffect, Element actionElement) {
        EffectType type = EffectType.valueOfRegexName(rawEffect.substring(0, rawEffect.indexOf(' ')));
        Matcher matcher = Pattern.compile(type.toRegex(true)).matcher(rawEffect);
        matcher.find();
        return switch (type) {
            case DAMAGE, HEAL -> {
                int hitRate = Integer.parseInt(matcher.group(ValueType.RATE.name()));
                TargetType target = TargetType.valueOfRegexName(matcher.group(TargetType.class.getSimpleName()));
                Power power = Power.create(matcher.group(Power.class.getSimpleName()).split(" "));
                if (type == EffectType.DAMAGE) {
                    yield new DamageEffect(hitRate, target, actionElement, power);
                }
                yield new HealEffect(hitRate, target, actionElement, power);
            }
            case INFLICT_STATUS_CONDITION -> new StatusConditionEffect(
                    Integer.parseInt(matcher.group(ValueType.RATE.name())),
                    TargetType.valueOfRegexName(matcher.group(TargetType.class.getSimpleName())),
                    Condition.valueOf(matcher.group(Condition.class.getSimpleName()))
            );
            case INFLICT_STAT_CHANGE -> new StatScaleEffect(
                    Integer.parseInt(matcher.group(ValueType.RATE.name())),
                    TargetType.valueOfRegexName(matcher.group(TargetType.class.getSimpleName())),
                    StatType.valueOf(matcher.group(StatType.class.getSimpleName())),
                    Integer.parseInt(matcher.group(ValueType.CHANGE.name()))
            );
            case PROTECT_STAT -> new ProtectEffect(
                    Integer.parseInt(matcher.group(ValueType.RATE.name())),
                    ProtectionType.valueOfRegexName(matcher.group(ProtectionType.class.getSimpleName())),
                    Count.create(matcher.group(Count.class.getSimpleName()).split(" "))
            );
            case CONTINUE -> new ContinueEffect(Integer.parseInt(matcher.group(ValueType.RATE.name())));
            case REPEAT -> new RepeatEffect(
                    Count.create(matcher.group(Count.class.getSimpleName()).split(" ")),
                    parseApplyableEffect(matcher.group(EffectType.class.getSimpleName()), actionElement)
            );
        };
    }

    private static int parseMonsters(String config) throws ConfigPatternException {
        Matcher matcher = Pattern.compile(MonsterSample.getRegex(false, true)).matcher(config);
        int count = 0;
        Set<String> names = new HashSet<>();
        while (matcher.find()) {
            String name = matcher.group(GROUP_NAME);
            if (names.contains(name)) {
                throw new ConfigPatternException(DUPLICATING_NAME_FORMAT.formatted("monster", name));
            } else {
                String[] actionNames = matcher.group(GROUP_ACTIONS).split(" ");
                checkActions(actionNames);
                new MonsterSample(name,
                        Element.valueOf(matcher.group(Element.class.getSimpleName())),
                        Integer.parseInt(matcher.group(ValueType.HEALTH.name())),
                        Integer.parseInt(matcher.group(ValueType.ATK.name())),
                        Integer.parseInt(matcher.group(ValueType.DEF.name())),
                        Integer.parseInt(matcher.group(ValueType.SPD.name())),
                        actionNames
                );
                count++;
                names.add(name);
            }
        }
        return count;
    }

    private static void checkActions(String[] actionNames) throws ConfigPatternException {
        for (String actionName : actionNames) {
            try {
                Action.find(actionName);
            } catch (GameRuntimeException e) {
                throw new ConfigPatternException(ACTION_NOT_FOUND_FORMAT.formatted(actionName));
            }
        }
    }

    /**
     * Builds and returns the aggregated regex pattern used to identify valid
     * configuration data. It combines the patterns needed for actions and monsters.
     *
     * @return a {@link String} representing the overall regex pattern.
     */
    public static String getRegex() {
        return RegexConstructor.groupAND(null, "",
                "(?:",
                RegexConstructor.group(null, Action.getRegex(false)),
                "*)",
                RegexConstructor.group(null, MonsterSample.getRegex(false, false)),
                "*"
        );
    }

}
