package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.power.Power;
import edu.kit.kastel.game.types.element.Element;

/**
 * An abstract class for effects that shift a monster's health,
 * potentially causing damage or healing.
 * <p>
 * This class extends {@link ApplyableEffect} and requires implementing
 * classes to define the impact on health through {@link #apply(Monster, Monster)}.
 * </p>
 *
 * @author uyqbd
 */
public abstract class HealthShiftEffect extends ApplyableEffect {
    private static final String MESSAGE_DEFEAT_FORMAT = "%s faints!%n";
    private static final String MASSAGE_PROTECTED_FORMAT = "%s is protected and takes no damage!%n";
    private static final String TAKING_DAMAGE_FORMAT = "%%s takes %%d damage%s!%%n";
    private static final String GAINING_HEALTH_FORMAT = "%%s gains back %%d health%s!%%n";
    private static final String REASON_FORMAT = " from %s";

    private final Element actionElement;
    private final Power power;
    private final int powerScale;
    private final String reason;

    /**
     * Constructs a {@code HealthShiftEffect} with the specified parameters.
     *
     * @param effectHitRate the base probability of this effect succeeding
     * @param target        whether this effect applies to the user or the target
     * @param actionElement the element used to calculate effectiveness
     * @param power         determines the magnitude of the health shift
     * @param powerScale    multiplies or inverts the power's value (+1 for healing, -1 for damage)
     */
    public HealthShiftEffect(int effectHitRate, TargetType target, Element actionElement, Power power, int powerScale) {
        this(effectHitRate, target, actionElement, power, powerScale, null);
    }

    /**
     * Constructs a {@code HealthShiftEffect} with an additional reason parameter.
     *
     * @param effectHitRate the base probability of this effect succeeding
     * @param target        whether this effect applies to the user or the target
     * @param actionElement the element used to calculate effectiveness
     * @param power         determines the magnitude of the health shift
     * @param powerScale    multiplies or inverts the power's value (+1 for healing, -1 for damage)
     * @param reason        an optional description for the shift (e.g., "poisoning")
     */
    public HealthShiftEffect(int effectHitRate, TargetType target, Element actionElement, Power power, int powerScale, String reason) {
        super(effectHitRate, target);
        this.actionElement = actionElement;
        this.power = power;
        this.powerScale = powerScale;
        this.reason = reason;
    }

    @Override
    public void apply(Monster userMonster, Monster targetMonster) {
        Monster target = isOnUser() ? userMonster : targetMonster;

        int shiftValue = powerScale * power.getValue(userMonster, target, actionElement);

        target.shiftHealth(shiftValue);
        Application.DEFAULT_OUTPUT_STREAM.printf((getMessageFormat(shiftValue)), target.getName(), Math.abs(shiftValue));

        if (target.isFainted()) {
            Application.DEFAULT_OUTPUT_STREAM.printf(MESSAGE_DEFEAT_FORMAT, target.getName());
        }
    }

    @Override
    public boolean canBeApplied(Monster user, Monster target, Boolean hits) {
        if (!isOnUser() && powerScale < 0 && target.getProtectionType() == ProtectionType.HEALTH) {
            Application.DEFAULT_OUTPUT_STREAM.printf(MASSAGE_PROTECTED_FORMAT, target.getName());
            return false;
        }
        return super.canBeApplied(user, target, hits);
    }

    private String getMessageFormat(int shiftValue) {
        return (shiftValue < 0 ? TAKING_DAMAGE_FORMAT : GAINING_HEALTH_FORMAT).formatted(
                reason == null ? "" : REASON_FORMAT.formatted(reason)
        );
    }

    @Override
    public Power getPower() {
        return power;
    }

    /**
     * Retrieves the elemental type used to calculate the effectiveness of this effect.
     *
     * @return the {@link Element} representing the action element
     */
    public Element getActionElement() {
        return actionElement;
    }

}