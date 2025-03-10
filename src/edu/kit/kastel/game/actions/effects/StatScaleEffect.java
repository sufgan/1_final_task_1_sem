package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.StatType;

/**
 * Adjusts a monster's stat (e.g., ATK, DEF, SPD, etc.) by a specified amount.
 * <p>
 * This effect can be blocked if the target has {@code STATS} protection.
 * </p>
 *
 * @author uyqbd
 */
public final class StatScaleEffect extends ApplyableEffect {
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and is unaffected!%n";
    private static final String POSITIVE_SHIFT_MESSAGE_END = "rises!";
    private static final String NEGATIVE_SHIFT_MESSAGE_END = "decreases...";
    private static final String MESSAGE_FORMAT = "%s's %s %s%n";

    private final StatType state;
    private final int scaleShift;

    /**
     * Creates a new {@code StatChangeEffect} that modifies the specified stat by
     * a certain amount.
     *
     * @param effectHitRate the base probability of this effect succeeding
     * @param target        specifies whether the effect applies to the user or opponent
     * @param state         the stat to be increased or decreased
     * @param scaleShift    the amount by which to modify the stat (negative for decrease, positive for increase)
     */
    public StatScaleEffect(int effectHitRate, TargetType target, StatType state, int scaleShift) {
        super(effectHitRate, target);
        this.state = state;
        this.scaleShift = scaleShift;
    }

    @Override
    public void apply(Monster user, Monster targetMonster) {
        Monster target = isOnUser() ? user : targetMonster;
        target.shiftScale(state, scaleShift);
        Application.DEFAULT_OUTPUT_STREAM.printf(MESSAGE_FORMAT,
                target.getName(),
                state,
                scaleShift < 0 ? NEGATIVE_SHIFT_MESSAGE_END : POSITIVE_SHIFT_MESSAGE_END);
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if (!this.isOnUser() && scaleShift < 0 && target.getProtectionType() == ProtectionType.STATS) {
            Application.DEFAULT_OUTPUT_STREAM.printf(MASSAGE_PROTECTED_FORMAT, target.getName());
            return false;
        }
        return super.canBeApplied(user, target);
    }

    @Override
    public ApplyableEffect copy() {
        return new StatScaleEffect(getHitRate(), getTarget(), state, scaleShift);
    }
}
