package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.types.StatType;

import java.util.Queue;

public abstract class ApplyableEffect extends Effect {
    private static final String DEBUG_MESSAGE = "apply action effect";

    private final int effectHitRate;
    private final TargetType target;

    public ApplyableEffect(int effectHitRate, TargetType target) {
        this.effectHitRate = effectHitRate;
        this.target = target;
    }

    public abstract boolean apply(Monster user, Monster target);

    protected boolean canBeApplied(Monster user, Monster target) {
        if (user.isFainted() || (!isOnUser() && target.isFainted())) {
            return false;
        }

        double userPRC = user.getStat(StatType.PRC);
        double targetAGL = isOnUser() ? 1 : target.getStat(StatType.AGL);
        double conditionQuotient = userPRC / targetAGL;

        return RandomGenerator.probabilityGood(effectHitRate * conditionQuotient, DEBUG_MESSAGE);
    }

    @Override
    public void addToEffectQueue(EffectQueue queue) {
        queue.add(this, true);
    }

    @Override
    public boolean needTarget() {
        return target == TargetType.TARGET;
    }

    public boolean isOnUser() {
        return target == TargetType.USER;
    }

    @Override
    public int getHitRate() {
        return effectHitRate;
    }
}
