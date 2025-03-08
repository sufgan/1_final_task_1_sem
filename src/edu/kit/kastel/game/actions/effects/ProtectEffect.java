package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.count.Count;

/**
 * Grants the user protection against either damage or status changes.
 * <p>
 * When applied, this effect sets a protection type on the user for a limited duration.
 * </p>
 *
 * @author uyqbd
 */
public final class ProtectEffect extends ApplyableEffect {
    private final ProtectionType protectionType;
    private final Count count;

    /**
     * Creates a {@code ProtectEffect} with the specified hit rate, protection type, and duration/count.
     *
     * @param effectHitRate   base probability of this effect succeeding
     * @param protectionType  the type of protection to be applied (e.g. HEALTH or STATUS)
     * @param count           the number of turns or instances this protection remains active
     */
    public ProtectEffect(int effectHitRate, ProtectionType protectionType, Count count) {
        super(effectHitRate, TargetType.USER);
        this.protectionType = protectionType;
        this.count = count;
    }

    @Override
    public boolean apply(Monster user, Monster target) {
        if (!canBeApplied(user, target)) {
            return false;
        }
        user.setProtection(protectionType, count.getValue());
        return true;
    }

}
