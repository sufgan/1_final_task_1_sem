package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.actions.effects.*;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.*;

public class Action {

    private static final Map<String, Action> ACTIONS = new HashMap<>();

    private final String name;
    private final Element element;
    private final List<Effect> effects;
    private final Queue<ApplyableEffect> effectsQueue;

    private final boolean needTarget;

    public Action(String name, Element element, List<Effect> effects) {
        this.name = name;
        this.element = element;
        this.effects = effects;
        needTarget = effectNeedTarget();
        this.effectsQueue = new LinkedList<>();
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

    public static Action find(String actionName) {
        return ACTIONS.getOrDefault(actionName, null);
    }

    public static void clearActions() {
        ACTIONS.clear();
    }

    public EffectQueue createEffectsQueue(Monster user, Monster target) {
        EffectQueue queue = new EffectQueue(name, user, target);
        for (Effect effect : effects) {
            effect.addToEffectQueue(queue);
        }
        return queue;
    }

    public String getName() {
        return name;
    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND( nameGroup ? Action.class.getSimpleName() : null, "",
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
