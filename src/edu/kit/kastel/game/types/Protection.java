package edu.kit.kastel.game.types;

import edu.kit.kastel.game.actions.effects.ProtectionType;

/**
 * Represents a protection effect with a specific type and limited duration.
 * This class is used to shield entities from various effects based on the
 * type of protection applied.
 *
 * @author uyqbd
 */
public class Protection {
    private final ProtectionType type;
    private int duration;

    /**
     * Constructs a new Protection instance with a specified type and duration.
     *
     * @param type the type of protection to be applied, defined by {@link ProtectionType}
     * @param duration the duration for which the protection is active, measured in steps
     */
    public Protection(ProtectionType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    /**
     * Reduces the remaining duration of this protection by one step.
     * If the duration reaches zero, the protection effect ends and {@code null} is returned.
     * Otherwise, the current instance of the protection is returned.
     *
     * @return the current {@code Protection} instance if the effect is still active,
     *         or {@code null} if the effect has ended
     */
    public Protection step() {
        return --duration == 0 ? null : this;
    }

    /**
     * Retrieves the type of protection associated with this instance.
     *
     * @return the {@link ProtectionType} that specifies the category of this protection
     */
    public ProtectionType getType() {
        return type;
    }

}