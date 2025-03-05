package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.power.RelativePower;

public final class BurnDamageEffect extends HealthShiftEffect {
    private static final String REASON = "burning";

    public BurnDamageEffect() {
        super(-1, TargetType.USER, null, new RelativePower(0.1), -1, REASON);
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        return true; // is always
    }

}
