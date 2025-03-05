package edu.kit.kastel.game.utils;

import edu.kit.kastel.ui.Reader;

import java.util.Random;

public final class RandomGenerator {
    private static final String PROBABILITY_DEBUG_MESSAGE_FORMAT = "Decide %s: yes or no (y/n)?";
    private static final String RANDOM_FACTOR_DEBUG_MESSAGE_FORMAT = "Decide %s: a double between %.2f and %.2f?";
    private static final String RANDOM_NUMBER_DEBUG_MESSAGE_FORMAT = "Decide %s: an integer between %d and %d?";

    private static final Random RANDOM = new Random();
    private static boolean debug = false;

    private RandomGenerator() {
        // util class
    }

    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
    }

    public static boolean probabilityGood(double probability, String debugMessage) {
        if (debug) {
            return Reader.readBoolean(PROBABILITY_DEBUG_MESSAGE_FORMAT.formatted(debugMessage));
        }
        return RANDOM.nextDouble() * 100 <= probability;
    }

    public static double getRandomFactor(double min, double max, String debugMessage) {
        if (debug) {
            return Reader.readDouble(RANDOM_FACTOR_DEBUG_MESSAGE_FORMAT.formatted(debugMessage, min, max));
        }
        return RANDOM.nextDouble(0.85, 1);
    }

    // TODO: Make repeatingscount generator
    public static int getRandomNumber(int min, int max, String debugMessage) {
        if (debug) {
            return Reader.readInteger(RANDOM_NUMBER_DEBUG_MESSAGE_FORMAT.formatted(debugMessage, min, max));
        }
        return RANDOM.nextInt(min, max + 1);
    }

    public static void switchDebugMod() {
        debug = !debug;
    }
}
