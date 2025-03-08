package edu.kit.kastel.utils;

import edu.kit.kastel.game.types.StatType;

/**
 * Utility class providing helper methods for operations involving numerical limits
 * and statistical scaling. This class cannot be instantiated and only exposes
 * static methods for usage.
 *
 * @author uyqbd
 */
public final class Utility {
    private Utility() {

    }

    /**
     * Restricts a given value within a specified range defined by a lower and upper bound.
     *
     * @param val the value to be constrained within the range
     * @param low the lower bound of the range
     * @param top the upper bound of the range
     * @return the value constrained within the range [low, top]. If the value is less than
     *         the lower bound, the lower bound is returned. If the value exceeds the upper bound,
     *         the upper bound is returned. Otherwise, the original value is returned.
     */
    public static int absLimitValue(int val, int low, int top) {
        return Math.max(low, Math.min(val, top));
    }

    /**
     * Returns the absolute value of {@code val} capped by {@code absTop}.
     *
     * <p>If the absolute value of {@code val} exceeds {@code absTop}, this method returns {@code absTop}.
     * Otherwise, it returns {@code Math.abs(val)}.</p>
     *
     * @param val    the original value
     * @param absTop the maximum absolute value that should not be exceeded
     * @return       the absolute value of {@code val}, limited by {@code absTop}
     */
    public static int absLimitValue(int val, int absTop) {
        return Math.min(Math.abs(val), absTop);
    }

    /**
     * Scales a given value based on the scaling factor of a provided stat type
     * and a specified scale value. The scaling behavior varies depending on
     * whether the scale is positive or negative.
     *
     * @param stat the stat type whose scaling factor is used in the calculation
     * @param val the value to be scaled
     * @param scale the scale value influencing the calculation; positive or negative
     *              values alter the resulting scaled value differently
     * @return the scaled value as a double
     */
    public static double scaleStat(StatType stat, int val, int scale) {
        double factor = stat.getFactor();
        return val * (scale >= 0 ? (factor + scale) / factor : factor / (factor - scale));
    }

    /**
     * Computes the ceiling division of two integers, which rounds the result of the
     * division of {@code a} by {@code b} upwards to the nearest integer.
     * This method avoids overflow issues that can occur with standard division operations.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the smallest integer greater than or equal to the result of {@code a / b}
     * @throws ArithmeticException if {@code b} is zero
     */
    public static int ceilDiv(int a, int b) {
        return -Math.floorDiv(-a, b);
    }

}
