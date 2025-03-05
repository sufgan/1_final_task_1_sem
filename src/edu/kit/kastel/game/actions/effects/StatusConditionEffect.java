package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.Condition;

public final class StatusConditionEffect extends ApplyableEffect {
    private final Condition condition;

    public StatusConditionEffect(int effectHitRate, TargetType target, Condition status) {
        super(effectHitRate, target);
        this.condition = status;
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        if (canBeApplied(user, target)) {
            (this.isOnUser() ? user : target).setCondition(condition);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if ((this.isOnUser() ? user : target).getCondition() == null && super.canBeApplied(user, target)) {
            System.out.printf(condition.getMessage(Condition.CREATING_MESSAGE),
                    (isOnUser() ? user : target).getName()
            );
            return true;
        }
        return false;
    }

}
