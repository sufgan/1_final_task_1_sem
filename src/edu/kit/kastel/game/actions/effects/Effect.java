package edu.kit.kastel.game.actions.effects;

import java.util.List;

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
     * Creates and returns a list of {@link ApplyableEffect} instances associated with this effect.
     * The generated effects define the specific behaviors or consequences to be applied
     * in the game context.
     *
     * @return a list of {@link ApplyableEffect} instances representing the effects to be applied
     */
    public abstract List<ApplyableEffect> create();

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
