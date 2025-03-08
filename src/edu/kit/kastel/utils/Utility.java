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
    public static int limitValue(int val, int low, int top) {
        return Math.max(low, Math.min(val, top));
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
     * Creates a new array by excluding an element from the input array at the specified index.
     *
     * @param <T> the type of elements in the array
     * @param array the input array from which an element will be excluded
     * @param index the index of the element to be excluded from the input array
     * @return a new array of the same type as the input array, but with the element at the specified index removed
     * @throws ArrayIndexOutOfBoundsException if the specified index is out of bounds for the input array
     */
    public static <T> T[] includeFromArray(T[] array, int index) {
        T[] result = (T[]) new Object[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, result.length - index);
        return result;
    }

}
