package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.game.types.StatType;

/**
 * An abstract effect that can be applied to a {@link Monster}.
 *
 * <p>Subclasses must implement {@link #apply(Monster, Monster)} to define
 * how the effect behaves.</p>
 *
 * @author uyqbd
 */
public abstract class ApplyableEffect extends Effect {
    private static final String DEBUG_MESSAGE = "apply action effect";

    private final int effectHitRate;
    private final TargetType target;

    /**
     * Constructs a new {@code ApplyableEffect} with a given hit rate and target type.
     *
     * @param effectHitRate the base probability of this effect succeeding
     * @param target        whether it applies to the user or the opponent
     */
    public ApplyableEffect(int effectHitRate, TargetType target) {
        this.effectHitRate = effectHitRate;
        this.target = target;
    }

    /**
     * Applies this effect from the {@code user} to the {@code target}.
     *
     * @param user   the monster using this effect
     * @param target the monster targeted by this effect
     * @return {@code true} if the effect was successfully applied; {@code false} otherwise
     */
    public abstract boolean apply(Monster user, Monster target);

    /**
     * Determines if this effect can be applied, considering both monsters' states
     * and a probability check based on user and target stats.
     *
     * @param user   the monster using this effect
     * @param target the monster targeted by this effect
     * @return {@code true} if the effect meets the conditions to be applied
     */
    protected boolean canBeApplied(Monster user, Monster target) {
        if (user.isFainted() || (!isOnUser() && target.isFainted())) {
            return false;
        }

        double userPRC = user.getStat(StatType.PRC);
        double targetAGL = isOnUser() ? 1 : target.getStat(StatType.AGL);
        double conditionQuotient = userPRC / targetAGL;

        return RandomGenerator.probabilityGood(effectHitRate * conditionQuotient, DEBUG_MESSAGE);
    }

    @Override
    public void addToEffectQueue(EffectQueue queue) {
        queue.add(this, true);
    }

    @Override
    public boolean needTarget() {
        return target == TargetType.TARGET;
    }

    @Override
    public int getHitRate() {
        return effectHitRate;
    }

    /**
     * Checks if this effect is meant to be applied to the user.
     *
     * @return {@code true} if the effect is on the user
     */
    public boolean isOnUser() {
        return target == TargetType.USER;
    }

}
