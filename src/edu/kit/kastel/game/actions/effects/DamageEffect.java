package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.Element;

/**
 * Represents a damage-inflicting effect that reduces health
 * based on the specified power and element.
 *
 * @author uyqbd
 */
public final class DamageEffect extends HealthShiftEffect {

    /**
     * Constructs a {@code DamageEffect} with a given hit rate, target, element, and power.
     *
     * @param effectHitRate  the base probability of this effect succeeding
     * @param target         specifies whether it applies to the user or the opponent
     * @param actionElement  the element associated with this damage effect
     * @param power          the power that determines the amount of damage
     */
    public DamageEffect(int effectHitRate, TargetType target, Element actionElement, Power power) {
        super(effectHitRate, target, actionElement, power, -1);
    }

}
