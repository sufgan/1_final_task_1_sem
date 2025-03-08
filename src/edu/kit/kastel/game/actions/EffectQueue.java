package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.BurnDamageEffect;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Condition;

import java.util.LinkedList;
import java.util.List;

/**
 * A queue for handling action and constant effects applied by a monster.
 * <p>
 * Applies action effects first; then applies any constant effects (e.g., burn damage).
 * </p>
 *
 * @author uyqbd
 */
public class EffectQueue {
    private static final String USE_ACTION_MESSAGE_FORMAT = "%s uses %s!%n";
    private static final String ACTION_FAIL_MESSAGE = "The action failed...";
    private static final String PASS_MESSAGE_FORMAT = "%s passes!%n";

    private final LinkedList<ApplyableEffect> constantEffects = new LinkedList<>();
    private final Action action;
    private final Monster user;
    private final Monster target;

    /**
     * Constructs an EffectQueue to handle effects applied by a monster's action.
     *
     * @param user   the monster performing the action
     * @param target the target monster of the action
     * @param action the action to be executed
     */
    public EffectQueue(Monster user, Monster target, Action action) {
        this.action = action;
        this.user = user;
        this.target = target;
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
        user.updateCondition();
        Condition userCondition = user.getCondition();
        List<ApplyableEffect> effects = action.createEffects();
        if (userCondition != null) {
            if (userCondition == Condition.SLEEP) {
                effects.clear();
            } else if (userCondition == Condition.BURN) {
                constantEffects.add(new BurnDamageEffect());
            }
        }

        applyActionEffects(effects);
        applyConstantEffects();
    }

    private void printMessage() {
        if (action.getName() != null) { // else is pass command
            System.out.printf(USE_ACTION_MESSAGE_FORMAT, user.getName(), action.getName());
        } else {
            System.out.printf(PASS_MESSAGE_FORMAT, user.getName());
        }
    }

    private void applyActionEffects(List<ApplyableEffect> effects) {
        if (!effects.isEmpty() && !effects.get(0).hits(user, target)) {
            System.out.println(ACTION_FAIL_MESSAGE);
            return;

        }

        for (ApplyableEffect effect : effects) {
            if (effect.canBeApplied(user, target)) {
                effect.apply(user, target);
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

}
