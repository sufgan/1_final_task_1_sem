package edu.kit.kastel.game.types.element;

/**
 * Represents the efficiency of an element in battle, providing a scaling value
 * used to determine the damage multiplier for the interaction between elements.
 * Element efficiency can indicate whether an interaction is normal, powerful,
 * or less effective.
 *
 * The enum contains three constants:
 * - {@code NORMAL}: Represents standard effectiveness with no damage modification.
 * - {@code POWERFUL}: Represents increased effectiveness with stronger damage.
 * - {@code POWERLESS}: Represents reduced effectiveness with diminished damage.
 *
 * @author uyqbd
 */
public enum ElementEfficiency {
    /**
     * Represents standard effectiveness with no damage modification.
     * The {@code NORMAL} constant corresponds to a default interaction
     * where neither amplified nor reduced damage multipliers are applied.
     */
    NORMAL(1, ""),
    /**
     * Represents an enhanced level of effectiveness in elemental interactions.
     * When an interaction is deemed "POWERFUL," it indicates that the damage
     * inflicted is increased based on the associated scaling factor.
     * The scaling value for "POWERFUL" effectiveness is {@code 2}, signifying
     * that the damage output is doubled compared to "NORMAL" effectiveness interactions.
     */
    POWERFUL(2, "It is very effective!"),
    /**
     * Represents reduced effectiveness, resulting in diminished damage during an
     * interaction between elements. The damage scale for this interaction is 0.5,
     * indicating that it is "not very effective."
     */
    POWERLESS(0.5, "It is not very effective...");

    private final String message;
    private final double damageScale;

    ElementEfficiency(double damageScale, String message) {
        this.message = message;
        this.damageScale = damageScale;
    }

    /**
     * Retrieves the damage scaling value associated with this {@code ElementEfficiency}.
     * The damage scale represents the multiplier applied to damage calculations
     * based on the effectiveness of an interaction between elements.
     *
     * @return the damage scaling value as a {@code double}
     */
    public double getDamageScale() {
        return damageScale;
    }

    /**
     * Retrieves the descriptive message associated with this {@code ElementEfficiency}.
     * The message provides details about the effectiveness of an interaction, such as
     * whether it is "very effective" or "not very effective."
     *
     * @return the descriptive message as a {@code String}
     */
    public String getMessage() {
        return message;
    }

}
