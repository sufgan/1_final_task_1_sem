package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.actions.EffectQueue;

/**
 * A base class for effects that can be applied within the game.
 * <p>
 * Each effect should specify how it's queued, whether it needs a target,
 * and its base hit rate.
 * </p>
 *
 * @author uyqbd
 */
public abstract class Effect {
    /**
     * Adds this effect to the specified {@link EffectQueue}.
     *
     * @param queue the queue to which this effect is added
     */
    public abstract void addToEffectQueue(EffectQueue queue);

    /**
     * Indicates if a separate target (i.e., not the user) is required.
     *
     * @return {@code true} if this effect needs a distinct target
     */
    public abstract boolean needTarget();

    /**
     * Retrieves the base hit rate of this effect.
     *
     * @return the hit rate as an integer
     */
    public abstract int getHitRate();

}
