package edu.kit.kastel.utils;

import java.util.Random;

/**
 * A utility class providing various methods for generating random numbers,
 * factors, and probabilities. The class supports a debug mode that allows user
 * input for deterministic behavior during testing or debugging.
 *
 * @author uyqbd
 */
public final class RandomGenerator {
    private static final String PROBABILITY_DEBUG_MESSAGE_FORMAT = "Decide %s: yes or no? (y/n)";
    private static final String RANDOM_FACTOR_DEBUG_MESSAGE_FORMAT = "Decide %s: a number between %.2f and %.2f?";
    private static final String RANDOM_NUMBER_DEBUG_MESSAGE_FORMAT = "Decide %s: an integer between %d and %d?";

    private static final Random RANDOM = new Random();
    private static boolean debug = false;

    private RandomGenerator() {

    }

    /**
     * Sets the seed for the internal random number generator.
     *
     * @param seed the initial seed to be set, affecting the sequence of
     *             generated random numbers.
     */
    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
    }

    /**
     * Determines if an event occurs based on a given probability.
     * If debug mode is enabled, the decision is prompted via user input.
     * Otherwise, the decision is made randomly using the specified probability.
     *
     * @param probability the probability (as a percentage, 0 to 100) that the event will occur
     * @param debugMessage the message to display if debug mode is enabled
     * @return {@code true} if the event occurs; {@code false} otherwise
     */
    public static boolean probabilityGood(double probability, String debugMessage) {
        if (debug) {
            return Reader.readBoolean(PROBABILITY_DEBUG_MESSAGE_FORMAT.formatted(debugMessage));
        }
        return RANDOM.nextDouble() * 100 <= probability;
    }

    /**
     * Generates a random factor within the specified range [min, max].
     * If debug mode is enabled, prompts the user for input instead of generating the factor.
     *
     * @param min the minimum bound of the range
     * @param max the maximum bound of the range
     * @param debugMessage the message displayed to the user when prompting in debug mode
     * @return a random double within the range [min, max], or the value entered by the user in debug mode
     */
    public static double getRandomFactor(double min, double max, String debugMessage) {
        if (debug) {
            return Reader.readDouble(RANDOM_FACTOR_DEBUG_MESSAGE_FORMAT.formatted(debugMessage, min, max));
        }
        return RANDOM.nextDouble(0.85, 1);
    }

    /**
     * Generates a random integer between the specified minimum and maximum values, inclusive.
     * If debug mode is enabled, prompts the user to input a value within the given range.
     *
     * @param min the minimum value for the random number (inclusive)
     * @param max the maximum value for the random number (inclusive)
     * @param debugMessage a message displayed to the user in debug mode when prompting for input
     * @return a random integer within the specified range or a user-provided value in debug mode
     */
    public static int getRandomNumber(int min, int max, String debugMessage) {
        if (debug) {
            return Reader.readInteger(RANDOM_NUMBER_DEBUG_MESSAGE_FORMAT.formatted(debugMessage, min, max));
        }
        return RANDOM.nextInt(min, max + 1);
    }

    /**
     * Toggles the debug mode of the RandomGenerator class.
     *
     * <p>
     * This method switches the internal debug mode state between enabled and
     * disabled. When debug mode is enabled, certain methods of the class
     * interact with the user for deterministic behavior. Otherwise, these
     * methods use randomly generated values.
     * </p>
     */
    public static void switchDebugMod() {
        debug = !debug;
    }

}
