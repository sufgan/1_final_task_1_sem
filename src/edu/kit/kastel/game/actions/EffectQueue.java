package edu.kit.kastel.game.actions;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.BurnDamageEffect;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.types.power.BasicPower;

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
public class EffectQueue implements Comparable<EffectQueue> {
    private static final String MONSTERS_TURN_FORMAT = "%nIt's %s's turn.%n";
    private static final String USE_ACTION_MESSAGE_FORMAT = "%s uses %s!%n";
    private static final String ACTION_FAIL_MESSAGE = "The action failed...";
    private static final String PASS_MESSAGE_FORMAT = "%s passes!%n";

    private final LinkedList<ApplyableEffect> constantEffects = new LinkedList<>();
    private final Action action;
    private final Monster user;
    private final Monster target;

    private boolean applyActionEffects = true;

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
        if (user.isFainted()) {
            return;
        }

        Application.DEFAULT_OUTPUT_STREAM.printf(MONSTERS_TURN_FORMAT, user.getName());

        List<ApplyableEffect> effects = action.createEffects();

        processCondition();
        applyActionEffects(effects);
        applyConstantEffects();
    }

    private void processCondition() {
        user.updateCondition();
        printMessage();
        Condition userCondition = user.getCondition();
        if (userCondition != null) {
            if (userCondition == Condition.SLEEP) {
                applyActionEffects = false;
            } else if (userCondition == Condition.BURN) {
                constantEffects.add(new BurnDamageEffect());
            }
        }
    }


    private void printMessage() {
        if (action.getName() != null) { // else is pass command
            Application.DEFAULT_OUTPUT_STREAM.printf(USE_ACTION_MESSAGE_FORMAT, user.getName(), action.getName());
        } else {
            Application.DEFAULT_OUTPUT_STREAM.printf(PASS_MESSAGE_FORMAT, user.getName());
        }
    }

    private void applyActionEffects(List<ApplyableEffect> effects) {
        if (!applyActionEffects) {
            return;
        }

        if (!effects.isEmpty() && !effects.get(0).hits(user, target)) {
            Application.DEFAULT_OUTPUT_STREAM.println(ACTION_FAIL_MESSAGE);
            return;
        }

        BasicPower.printNextElementEfficiency();
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

    @Override
    public int compareTo(EffectQueue o) {
        return user.compareTo(o.user);
    }

}
