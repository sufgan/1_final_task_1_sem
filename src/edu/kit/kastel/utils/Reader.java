package edu.kit.kastel.utils;

import edu.kit.kastel.Application;

import java.util.regex.Pattern;

/**
 * Utility class for reading user input with validation.
 * <p>
 * Provides methods to read boolean, double, and integer values from the console.
 * Each method displays a prompt message and validates the input format.
 * If the input is invalid, an error message is printed and the prompt is repeated.
 * </p>
 *
 * @author uyqbd
 */
public final class Reader {
    private static final String ERROR_MESSAGE = "Error, wrong value.";
    private static final String DOUBLE_REGEX = "\\d+(\\.\\d+)?";
    private static final String INTEGER_REGEX = "\\d+";

    private Reader() {

    }

    /**
     * Reads a boolean value from the user.
     * <p>
     * Prompts the user with the specified message. Accepts a single character: 'y' returns true,
     * and 'n' returns false. If the input is invalid, an error message is printed and the prompt repeats.
     * </p>
     *
     * @param message the prompt message to display
     * @return {@code true} if the user enters 'y'; {@code false} if the user enters 'n'
     */
    public static boolean readBoolean(String message) {
        Application.DEFAULT_OUTPUT_STREAM.println(message);
        String answer = Application.readInputLine();
        if (!answer.isEmpty()) {
            switch (answer) {
                case "y": return true;
                case "n": return false;
                default: checkOnCommands(answer);
            }
        }
        Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        return readBoolean(message);
    }

    /**
     * Reads a double value from the user.
     * <p>
     * Prompts the user with the specified message and expects a decimal number in the format
     * "digits.digits". If the input does not match the required format, an error message is printed
     * and the prompt repeats.
     * </p>
     *
     * @param message the prompt message to display
     * @return the double value entered by the user
     */
    public static double readDouble(String message) {
        Application.DEFAULT_OUTPUT_STREAM.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches(DOUBLE_REGEX, answer)) {
            return Double.parseDouble(answer);
        } else {
            checkOnCommands(answer);
        }
        Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        return readDouble(message);
    }

    /**
     * Reads an integer value from the user.
     * <p>
     * Prompts the user with the specified message and expects a whole number.
     * If the input does not consist solely of digits, an error message is printed and the prompt repeats.
     * </p>
     *
     * @param message the prompt message to display
     * @return the integer value entered by the user
     */
    public static int readInteger(String message) {
        Application.DEFAULT_OUTPUT_STREAM.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches(INTEGER_REGEX, answer)) {
            return Integer.parseInt(answer);
        } else {
            checkOnCommands(answer);
        }
        Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        return readInteger(message);
    }

    private static void checkOnCommands(String command) {

    }

}
