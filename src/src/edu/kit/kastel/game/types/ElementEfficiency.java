package edu.kit.kastel.game.types;

public enum ElementEfficiency {
    NORMAL(1),
    POWERFUL(2),
    POWERLESS(0.5);

    private final double damageScale;

    ElementEfficiency(double damageScale) {
        this.damageScale = damageScale;
    }

    public double getDamageScale() {
        return damageScale;
    }

}
