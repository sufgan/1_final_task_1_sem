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
    public void apply(Monster user, Monster targetMonster) {
        Monster target = isOnUser() ? user : targetMonster;
        target.setCondition(condition);
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        return target.getCondition() == null && super.canBeApplied(user, target);
    }

    @Override
    public ApplyableEffect copy() {
        return new StatusConditionEffect(getHitRate(), getTarget(), condition);
    }

}
