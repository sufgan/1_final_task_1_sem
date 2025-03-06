package edu.kit.kastel.game.actions;


import edu.kit.kastel.game.actions.effects.DamageEffect;
import edu.kit.kastel.game.actions.effects.Effect;
import edu.kit.kastel.game.actions.effects.EffectType;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

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
     * Finds an existing {@code Action} by its name.
     *
     * @param actionName the name of the action to look up
     * @return the {@code Action} if found; otherwise {@code null}
     */
    public static Action find(String actionName) {
        return ACTIONS.getOrDefault(actionName, null);
    }

    /**
     * Clears all registered actions from the internal storage.
     */
    public static void clearActions() {
        ACTIONS.clear();
    }

    /**
     * Creates an {@link EffectQueue} for this action, associating it with a user monster and a target monster.
     *
     * @param user   the monster executing this action
     * @param target the monster targeted by this action
     * @return a new {@link EffectQueue} containing the effects of this action
     */
    public EffectQueue createEffectsQueue(Monster user, Monster target) {
        EffectQueue queue = new EffectQueue(name, user, target);
        for (Effect effect : effects) {
            effect.addToEffectQueue(queue);
        }
        return queue;
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

    private List<String> findDamages() {
        List<String> damages = new LinkedList<>();
        for (Effect effect : effects) {
            if (effect instanceof DamageEffect) {
                damages.add(((DamageEffect) effect).getPower().toString());
            }
        }
        if (damages.isEmpty()) {
            damages.add("--");
        }
        return damages;
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
        return "%s: ELEMENT %s, Damage %s, HitRate %s".formatted(
                name,
                element,
                String.join(",", findDamages()),
                effects.get(0).getHitRate()
        );
    }

}
