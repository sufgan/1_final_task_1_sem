package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.GameRuntimeException;
import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.DamageEffect;
import edu.kit.kastel.game.actions.effects.Effect;
import edu.kit.kastel.game.actions.effects.EffectType;
import edu.kit.kastel.game.actions.effects.RepeatEffect;
import edu.kit.kastel.game.types.element.Element;
import edu.kit.kastel.utils.RegexConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a named action with an associated {@link Element} and a list of {@link Effect} instances.
 * <p>
 * Actions can be looked up, cleared, and used to create an {@link EffectQueue} for execution.
 * </p>
 *
 * @author uyqbd
 */
public class Action {
    private static final String NO_DAMAGE = "--";
    private static final String PRINT_FORMAT = "%s: ELEMENT %s, Damage %s, HitRate %s";
    private static final String ACTION_NOT_FOUND_FORMAT = "action %s not found";

    /**
     * A predefined, immutable instance of {@code Action} with no name, no elemental type,
     * and an empty list of effects. This represents an action that performs no operation,
     * often used as a placeholder or default value.
     */
    public static final Action EMPTY_ACTION = new Action(null, null, List.of());

    private static final Map<String, Action> ACTIONS = new HashMap<>();

    private final String name;
    private final Element element;
    private final List<Effect> effects;
    private final boolean needTarget;

    /**
     * Constructs a new {@code Action} with a given name, element, and list of effects.
     *
     * @param name    the action's name
     * @param element the elemental type associated with this action
     * @param effects the list of {@link Effect} instances that define this action's behavior
     */
    public Action(String name, Element element, List<Effect> effects) {
        this.name = name;
        this.element = element;
        this.effects = effects;
        needTarget = effectNeedTarget();
        if (name != null) {
            ACTIONS.put(name, this);
        }
    }

    private boolean effectNeedTarget() {
        for (Effect effect : effects) {
            if (effect.needTarget()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates and retrieves a list of {@link ApplyableEffect} instances based on the current action's
     * associated effects. It iterates through all {@link Effect} objects in the action, invoking
     * their {@code create} method to generate applicable effects.
     *
     * @return a list of {@link ApplyableEffect} objects derived from the current action's effects
     */
    public List<ApplyableEffect> createEffects() {
        List<ApplyableEffect> effects = new LinkedList<>();
        for (Effect effect : this.effects) {
            effects.addAll(effect.create());
        }
        return effects;
    }

    /**
     * Finds and retrieves an {@code Action} instance by its name from the internal storage.
     *
     * @param actionName the name of the action to retrieve
     * @return the {@code Action} instance associated with the provided name
     * @throws GameRuntimeException if the action with the given name cannot be found
     */
    public static Action find(String actionName) throws GameRuntimeException {
        if (ACTIONS.containsKey(actionName)) {
            return ACTIONS.get(actionName);
        }
        throw new GameRuntimeException(ACTION_NOT_FOUND_FORMAT.formatted(actionName));
    }

    /**
     * Clears all registered actions from the internal storage.
     */
    public static void clearActions() {
        ACTIONS.clear();
    }

    /**
     * Returns the name of this action.
     *
     * @return the action's name
     */
    public String getName() {
        return name;
    }

    /**
     * Provides a regex pattern for matching an action's configuration.
     *
     * @param nameGroup {@code true} to include a named group in the pattern
     * @return a regex {@code String} used to parse action definitions
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? Action.class.getSimpleName() : null, "",
                RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE,
                        "action",
                        "(?%s\\w+)".formatted(nameGroup ? "<name>" : ":"),
                        Element.getRegex(nameGroup)
                ),
                RegexConstructor.REGEX_NEW_LINE,
                RegexConstructor.groupAND(nameGroup ? EffectType.class.getSimpleName() : null, "",
                        RegexConstructor.group(null, EffectType.getRegex(false)),
                        "+"
                ),
                "end action",
                RegexConstructor.REGEX_MULTI_NEW_LINE
        );
    }

    /**
     * Returns a simple regex pattern for an action that requires or doesn't require a target,
     * using the provided monster regex if needed.
     *
     * @param monstersRegex the regex pattern representing a target monster
     * @return a {@code String} that joins the action name and optionally the monsters regex
     */
    public String toRegex(String monstersRegex) {
        List<String> elements = new LinkedList<>();
        elements.add(name);
        if (needTarget) {
            elements.add(monstersRegex);
        }
        return String.join(" ", elements);
    }

    private String findDamage() {
        return findDamage(effects);
    }

    private String findDamage(List<Effect> effects) {
        for (Effect effect : effects) {
            if (effect instanceof DamageEffect) {
                return ((DamageEffect) effect).getPower().toString();
            } else if (effect instanceof RepeatEffect) {
                String damage = findDamage(((RepeatEffect) effect).getEffects());
                if (!damage.equals(NO_DAMAGE)) {
                    return damage;
                }
            }
        }
        return NO_DAMAGE;
    }

    /**
     * Checks whether this action requires a target monster or not.
     *
     * @return {@code true} if any of the action's effects requires a target; otherwise {@code false}
     */
    public boolean needTarget() {
        return needTarget;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(
                name,
                element,
                findDamage(),
                effects.get(0).getHitRate()
        );
    }

}
