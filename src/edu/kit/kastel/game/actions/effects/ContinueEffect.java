package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;

/**
 * Represents an effect allowing the action to continue on the user.
 *
 * <p>
 * This effect only checks the hit probability and doesn't modify stats or health.
 * </p>
 *
 * @author uyqbd
 */
public final class ContinueEffect extends ApplyableEffect {
    /**
     * Creates a new {@code ContinueEffect} with the specified hit rate.
     *
     * @param effectHitRate the base probability of this effect succeeding
     */
    public ContinueEffect(int effectHitRate) {
        super(effectHitRate, TargetType.USER);
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        return canBeApplied(user, target);
    }

}
