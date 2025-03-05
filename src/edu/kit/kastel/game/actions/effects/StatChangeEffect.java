package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.StatType;

public final class StatChangeEffect extends ApplyableEffect {
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and is unaffected!%n";
    private static final String POSITIVE_SHIFT_MESSAGE_END = "rises!";
    private static final String NEGATIVE_SHIFT_MESSAGE_END = "decreases...";
    private static final String MESSAGE_FORMAT = "%s's %s %s%n";

    private final StatType state;
    private final int scaleShift;

    public StatChangeEffect(int effectHitRate, TargetType target, StatType state, int scaleShift) {
        super(effectHitRate, target);
        this.state = state;
        this.scaleShift = scaleShift;
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        if (!canBeApplied(user, target)) {
            return false;
        }
        (this.isOnUser() ? user : target).shiftScale(state, scaleShift);

        System.out.printf(MESSAGE_FORMAT,
                (this.isOnUser() ? user : target).getName(),
                state,
                scaleShift < 0 ? NEGATIVE_SHIFT_MESSAGE_END : POSITIVE_SHIFT_MESSAGE_END);
        return true;
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if (!this.isOnUser() && target.getProtectionType() == ProtectionType.STATS) { // check protection
            System.out.printf(MASSAGE_PROTECTED_FORMAT, target.getName());
            return false;
        }
        return super.canBeApplied(user, target); // check hit
    }

    public static Effect createEffect(String regex) {
        return null;
    }

}
