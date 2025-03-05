package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;

public final class ContinueEffect extends ApplyableEffect {
    public static final String NAME = "continue";

    public ContinueEffect(int effectHitRate) {
        super(effectHitRate, TargetType.USER);
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        return canBeApplied(user, target);
    }

}
