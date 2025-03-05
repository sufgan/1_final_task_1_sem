package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.actions.EffectQueue;

public abstract class Effect {
    public abstract void addToEffectQueue(EffectQueue queue);

    public abstract boolean needTarget();

    public abstract int getHitRate();
}
