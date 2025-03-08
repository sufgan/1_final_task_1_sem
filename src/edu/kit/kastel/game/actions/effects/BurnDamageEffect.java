package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.power.RelativePower;

/**
 * A specialized health-shift effect that inflicts burn damage on the user.
 *
 * <p>This effect always applies, ignoring standard conditions.</p>
 *
 * @author uyqbd
 */
public final class BurnDamageEffect extends HealthShiftEffect {
    private static final String REASON = "burning";

    /**
     * Constructs a new {@code BurnDamageEffect} that applies
     * a 10% health reduction to the user each turn.
     */
    public BurnDamageEffect() {
        super(0, TargetType.USER, null, new RelativePower(10), -1, REASON);
    }

    @Override
    public boolean hits(Monster user, Monster target) {
        return true;
    }

    @Override
    public ApplyableEffect copy() {
        return new BurnDamageEffect();
    }
}
