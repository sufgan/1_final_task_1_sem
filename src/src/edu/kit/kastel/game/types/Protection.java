package edu.kit.kastel.game.types;

import edu.kit.kastel.game.actions.effects.ProtectionType;

public class Protection {
    private final ProtectionType type;
    private int duration;

    public Protection(ProtectionType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public Protection step() {
        return --duration == 0 ? null : this;
    }

    public ProtectionType getType() {
        return type;
    }

    public boolean isFinished() {
        return duration == 0;
    }

}