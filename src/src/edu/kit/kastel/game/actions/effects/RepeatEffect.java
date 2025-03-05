package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.types.count.Count;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class RepeatEffect extends Effect {
    private final List<ApplyableEffect> effects;
    private final Count count;

    public RepeatEffect(Count count, List<ApplyableEffect> effects) {
        this.count = count;
        this.effects = new LinkedList<>(effects);
    }

    @Override
    public void addToEffectQueue(EffectQueue queue) {
        for (int i = 0; i < count.getValue(); i++) {
            queue.addAll(effects, true);
        }
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
}
