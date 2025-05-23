package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.types.count.Count;
import edu.kit.kastel.game.types.power.Power;

import java.util.LinkedList;
import java.util.List;

/**
 * Repeats a set of {@link ApplyableEffect} instances a specified number of times.
 * <p>
 * Uses the {@link Count} value to determine how many times to queue the effects.
 * </p>
 *
 * @author uyqbd
 */
public final class RepeatEffect extends Effect {
    private static final String DEBUG_MESSAGE = "count of repeating";

    private final List<ApplyableEffect> effects;
    private final Count count;

    /**
     * Constructs a {@code RepeatEffect} which repeats the given effects for the specified count.
     *
     * @param count   how many times to repeat the effects
     * @param effects the list of {@link ApplyableEffect} to be repeated
     */
    public RepeatEffect(Count count, List<ApplyableEffect> effects) {
        this.count = count;
        this.effects = new LinkedList<>(effects);
    }

    @Override
    public List<ApplyableEffect> create() {
        List<ApplyableEffect> effects = new LinkedList<>();
        int count = this.count.getValue(DEBUG_MESSAGE);
        for (int i = 0; i < count; i++) {
            for (ApplyableEffect effect : this.effects) {
                effects.add(effect.copy());
            }
        }
        return effects;
    }

    @Override
    public boolean needTarget() {
        for (ApplyableEffect effect : effects) {
            if (effect.needTarget()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getHitRate() {
        return effects.get(0).getHitRate();
    }

    @Override
    public Power getPower() {
        for (ApplyableEffect effect : effects) {
            if (effect.getPower() != null) {
                return effect.getPower();
            }
        }
        return null;
    }

    /**
     * Retrieves the list of {@link ApplyableEffect} contained in this RepeatEffect.
     *
     * @return the list of effects
     */
    public List<Effect> getEffects() {
        return new LinkedList<>(effects);
    }
    
    
}
