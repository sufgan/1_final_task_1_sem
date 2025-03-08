package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.BurnDamageEffect;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.types.StatType;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A queue for handling action and constant effects applied by a monster.
 * <p>
 * Applies action effects first; then applies any constant effects (e.g., burn damage).
 * </p>
 *
 * @author uyqbd
 */
public class EffectQueue implements Comparable<EffectQueue> {
    private static final String USE_ACTION_MESSAGE_FORMAT = "%s uses %s!%n";
    private static final String ACTION_FAIL_MESSAGE = "The action failed...";
    private static final String PASS_MESSAGE_FORMAT = "%s passes!%n";

    private final LinkedList<ApplyableEffect> actionEffects = new LinkedList<>();
    private final LinkedList<ApplyableEffect> constantEffects = new LinkedList<>();
    private final String actionName;
    private final Monster user;
    private final Monster target;

    /**
     * Constructs a new {@code EffectQueue} for the given action name and monsters.
     *
     * @param actionName name of the action being performed
     * @param user       the monster performing this action
     * @param target     the monster targeted by this action
     */
    public EffectQueue(String actionName, Monster user, Monster target) {
        this.actionName = actionName;
        this.user = user;
        this.target = target;
    }

    /**
     * Adds a single effect to either the action effect list or the constant effect list.
     *
     * @param effect       the {@link ApplyableEffect} to be added
     * @param actionEffect if {@code true}, adds to the action effect list; otherwise adds to the constant effect list
     */
    public void add(ApplyableEffect effect, boolean actionEffect) {
        if (actionEffect) {
            actionEffects.add(effect);
        } else {
            constantEffects.add(effect);
        }
    }

    /**
     * Adds multiple effects to either the action effect list or the constant effect list.
     *
     * @param effect       a collection of {@link ApplyableEffect} instances to be added
     * @param actionEffect if {@code true}, adds to the action effect list; otherwise adds to the constant effect list
     */
    public void addAll(Collection<ApplyableEffect> effect, boolean actionEffect) {
        if (actionEffect) {
            actionEffects.addAll(effect);
        } else {
            constantEffects.addAll(effect);
        }
    }

    /**
     * Applies all effects in this queue to the appropriate monsters.
     * <p>
     * Prints action usage or pass messages. If the user is asleep, action effects are cleared;
     * if burned, a {@link BurnDamageEffect} is added to constant effects.
     * Action effects are applied first. If the first action effect fails,
     * remaining action effects are skipped. Constant effects are always applied.
     * </p>
     */
    public void apply() {
        printMessage();
        Condition userCondition = user.getCondition();
        if (userCondition != null) {
            if (userCondition == Condition.SLEEP) {
                actionEffects.clear();
            } else if (userCondition == Condition.BURN) {
                constantEffects.add(new BurnDamageEffect());
            }
        }

        applyActionEffects();
        applyConstantEffects();
    }

    private void printMessage() {
        if (actionName != null) { // else is pass command
            System.out.printf(USE_ACTION_MESSAGE_FORMAT, user.getName(), actionName);
        } else {
            System.out.printf(PASS_MESSAGE_FORMAT, user.getName());
        }
    }

    private void applyActionEffects() {
        if (!actionEffects.isEmpty()) {
            ApplyableEffect effect = actionEffects.pollFirst();
            if (!effect.hits(user, target)) {
                System.out.println(ACTION_FAIL_MESSAGE);
                return;
            }
            effect.apply(user, target);

            while (!actionEffects.isEmpty()) {
                effect = actionEffects.pollFirst();
                if (effect.canBeApplied(user, target)) {
                    effect.apply(user, target);
                }
            }
        }
    }

    private void applyConstantEffects() {
        for (ApplyableEffect effect : constantEffects) {
            effect.apply(user, target);
        }
    }

    /**
     * Returns the monster performing the action.
     *
     * @return the user monster
     */
    public Monster getUser() {
        return user;
    }

    @Override
    public int compareTo(EffectQueue o) {
        return -Double.compare(user.getStat(StatType.SPD), o.user.getStat(StatType.SPD));
    }

    @Override
    public String toString() {
        return "%s:%nAE -> %s%nCE -> %s".formatted(actionName, actionEffects, constantEffects);
    }

}
