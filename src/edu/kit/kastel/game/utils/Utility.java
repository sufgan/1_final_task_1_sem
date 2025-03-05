package edu.kit.kastel.game.utils;

import edu.kit.kastel.game.types.StatType;

public final class Utility {
    private Utility() {

    }

    public static int limitValue(int val, int low, int top) {
        return Math.max(low, Math.min(val, top));
    }

    public static double scaleStat(StatType stat, int val, int scale) {
        double factor = stat.getFactor();
        return val * (scale >= 0 ?
                (factor + scale) / factor :
                factor / (factor - scale));
    }

}
