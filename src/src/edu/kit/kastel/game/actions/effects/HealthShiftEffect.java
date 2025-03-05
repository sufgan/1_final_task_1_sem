package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.Element;

public abstract class HealthShiftEffect extends ApplyableEffect {
    private static final String MESSAGE_DEFEAT_FORMAT = "%s faints!%n";
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and takes no damage!%n";

    private final Element actionElement;
    private final Power power;
    private final int powerScale;
    private final String reason;

    public HealthShiftEffect(int effectHitRate, TargetType target, Element actionElement, Power power, int powerScale) {
        this(effectHitRate, target, actionElement, power, powerScale, null);
    }

    public HealthShiftEffect(int effectHitRate, TargetType target, Element actionElement, Power power, int powerScale, String reason) {
        super(effectHitRate, target);
        this.actionElement = actionElement;
        this.power = power;
        this.powerScale = powerScale;
        this.reason = reason;
    }

    @Override
    public boolean apply(Monster userMonster, Monster targetMonster) {
        Monster target = isOnUser() ? userMonster : targetMonster;
        if (!canBeApplied(userMonster, target)) {
            return false;
        }
        int shiftValue = powerScale * power.getValue(userMonster, target, actionElement);
        target.shiftHealth(shiftValue);
        System.out.printf((getMessageFormat(shiftValue)), target.getName(), Math.abs(shiftValue));

        if (target.isFainted()) {
            System.out.printf(MESSAGE_DEFEAT_FORMAT, target.getName());
        }
        return true;
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target) {
        if (!this.isOnUser() && target.getProtectionType() == ProtectionType.HEALTH) { // check protection
            System.out.printf(MASSAGE_PROTECTED_FORMAT, (this.isOnUser() ? user : target).getName());
            return false;
        }
        return super.canBeApplied(user, target); // check hit
    }

    private String getMessageFormat(int shiftValue) {
        return (shiftValue < 0 ? "%%s takes %%d damage%s%%n" : "%%s gains back %%d health%s!%%n").formatted(
                reason == null ? "" : " from %s".formatted(reason)
        );
    }

    public Power getPower() {
        return power;
    }

}