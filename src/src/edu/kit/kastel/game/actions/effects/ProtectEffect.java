package edu.kit.kastel.game.actions.effects;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.count.Count;

public final class ProtectEffect extends ApplyableEffect {
    private final ProtectionType protectionType;
    private final Count count;

    public ProtectEffect(int effectHitRate, ProtectionType protectionType, Count count) {
        super(effectHitRate, TargetType.USER);
        this.protectionType = protectionType;
        this.count = count;
    }

    // TODO: изменить на защиту только самого себя
    @Override
    public boolean apply(Monster user, Monster target) {
        if (!canBeApplied(user, target)) {
            return false;
        }
        user.setProtection(protectionType, count.getValue());

        System.out.printf("%s is now protected against %s!%n",
                user.getName(),
                protectionType == ProtectionType.HEALTH ? "damage" : "status changes");
        return true;
    }

    public static Effect createEffect(String regex) {
        return null;
    }

}
