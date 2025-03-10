package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.game.types.StatType;

import java.util.List;

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
     */
    public abstract void apply(Monster user, Monster target);

    /**
     * Determines whether the effect can be applied from the user to the target,
     * considering whether the action successfully "hits" based on the provided
     * 'hits' parameter or recalculating it if 'hits' is null.
     *
     * @param user   the monster attempting to use this effect
     * @param target the monster that is targeted by this effect
     * @param hits   the predefined result of whether the effect hits, or {@code null}
     *               if the hit success should be recalculated
     * @return {@code true} if the effect can be applied, otherwise {@code false}
     */
    public boolean canBeApplied(Monster user, Monster target, Boolean hits) {
        return hits == null ? hits(user, target) : hits;
    }

    /**
     * Determines if an effect successfully "hits" the target by comparing the user's precision
     * and the target's agility, factoring in the predefined effect hit rate and a random probability check.
     *
     * @param user   the monster using this effect, whose precision is considered in the calculation
     * @param target the monster targeted by this effect, whose agility is referenced if the effect is applied to a target
     * @return {@code true} if the effect hits the target, otherwise {@code false}
     */
    public boolean hits(Monster user, Monster target) {
        if (user.isFainted() || (!isOnUser() && target.isFainted())) {
            return false;
        }

        double userPRC = user.getStat(StatType.PRC);
        double targetAGL = isOnUser() ? 1 : target.getStat(StatType.AGL);
        double conditionQuotient = userPRC / targetAGL;

        return RandomGenerator.probabilityGood(effectHitRate * conditionQuotient, DEBUG_MESSAGE);
    }

    @Override
    public List<ApplyableEffect> create() {
        return List.of(this);
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
     * Retrieves the target type of this effect.
     *
     * @return the target type indicating whether the effect is applied to the user or another entity
     */
    public TargetType getTarget() {
        return target;
    }

    /**
     * Checks if this effect is meant to be applied to the user.
     *
     * @return {@code true} if the effect is on the user
     */
    public boolean isOnUser() {
        return target == TargetType.USER;
    }
    
    /**
     * Creates and returns a copy of this {@code ApplyableEffect}.
     * The copied instance retains all properties and configurations
     * of the original effect, allowing for independent modification
     * or reapplication.
     *
     * @return a new {@code ApplyableEffect} instance that is a clone of this effect
     */
    public abstract ApplyableEffect copy();

    @Override
    public ApplyableEffect asApplyableEffect() {
        return this;
    }

}
