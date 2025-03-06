package edu.kit.kastel.game.types;

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
    NORMAL(1),
    POWERFUL(2),
    POWERLESS(0.5);

    private final double damageScale;

    ElementEfficiency(double damageScale) {
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

}
