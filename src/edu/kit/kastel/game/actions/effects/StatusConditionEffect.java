package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Condition;

/**
 * Applies a {@link Condition} status effect to a monster, if conditions allow.
 * <p>
 * For instance, this effect can set a monster's condition to POISONED or PARALYZED.
 * </p>
 *
 * @author uyqbd
 */
public final class StatusConditionEffect extends ApplyableEffect {
    private final Condition condition;

    /**
     * Creates a {@code StatusConditionEffect} with a given hit rate, target, and condition.
     *
     * @param effectHitRate the base probability of this effect succeeding
     * @param target        whether it applies to the user or the opponent
     * @param status        the status condition to be applied (e.g., POISON)
     */
    public StatusConditionEffect(int effectHitRate, TargetType target, Condition status) {
        super(effectHitRate, target);
        this.condition = status;
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        if (canBeApplied(user, target)) {
            (this.isOnUser() ? user : target).setCondition(condition);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if ((this.isOnUser() ? user : target).getCondition() == null && super.canBeApplied(user, target)) {
            System.out.printf(condition.getMessage(Condition.CREATING_MESSAGE),
                    (isOnUser() ? user : target).getName()
            );
            return true;
        }
        return false;
    }

}
