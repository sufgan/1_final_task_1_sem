package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.Element;

public final class DamageEffect extends HealthShiftEffect {
    public DamageEffect(int effectHitRate, TargetType target, Element actionElement, Power power) {
        super(effectHitRate, target, actionElement, power, -1);
    }

}
