package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.element.Element;

/**
 * An effect that restores health based on the specified power.
 * <p>
 * Increases the target's health when applied successfully.
 * </p>
 *
 * @author uyqbd
 */
public final class HealEffect extends HealthShiftEffect {
    private static final int POWER_SCALE = 1;

    /**
     * Creates a {@code HealEffect} with a given hit rate, target, element, and power.
     *
     * @param effectHitRate  the base probability of this effect succeeding
     * @param target         specifies whether it applies to the user or the opponent
     * @param actionElement  the element associated with this healing
     * @param power          the power determining the healing amount
     */
    public HealEffect(int effectHitRate, TargetType target, Element actionElement, Power power) {
        super(effectHitRate, target, actionElement, power, POWER_SCALE);
    }

    @Override
    public ApplyableEffect copy() {
        return new HealEffect(getHitRate(), getTarget(), getActionElement(), getPower());
    }

}

