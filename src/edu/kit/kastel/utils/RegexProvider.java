package edu.kit.kastel.utils;

/**
 * Represents an interface for converting objects into regular expressions.
 * Classes implementing this interface should define behavior for converting
 * their specific representations into a regex pattern.
 *
 * @author uyqbd
 */
public interface RegexProvider {
    /**
     * Converts the implementing object's representation into a regex pattern.
     * Optionally allows naming the group in the resulting pattern.
     *
     * @param nameGroup a boolean indicating whether the group in the regex
     *                  representation should be named. If {@code true}, the group
     *                  will be named; otherwise, it will remain unnamed.
     * @return a string representing the regex pattern of the object.
     */
    String toRegex(boolean nameGroup);

}
