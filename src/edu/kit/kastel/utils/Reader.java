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
//        System.out.println();
        System.out.println(message);
        String answer = Application.readInputLine();
        if (answer.length() == 1) {
            switch (answer.charAt(0)) {
                case 'y': return true;
                case 'n': return false;
                default: break;
            }
        }
        System.out.println("Error, enter y or n.");
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
//        System.out.println();
        System.out.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches("\\d+\\.\\d+", answer)) {
            return Double.parseDouble(answer);
        }
        System.out.println(ERROR_MESSAGE);
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
//        System.out.println();
        System.out.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches("\\d+", answer)) {
            return Integer.parseInt(answer);
        }
        System.err.println(ERROR_MESSAGE);
        return readInteger(message);
    }

}
