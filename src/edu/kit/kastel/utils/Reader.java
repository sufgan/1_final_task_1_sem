package edu.kit.kastel.utils;

import edu.kit.kastel.Application;

import java.util.Scanner;
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

    private static Scanner scanner;

    private Reader() {

    }

    /**
     * Reads a boolean value from the user after displaying a specified prompt message.
     * The user is expected to enter either 'y' or 'n'. If the input is invalid,
     * an error message is printed and the prompt repeats until a valid input is provided.
     *
     * @param message the prompt message to display to the user
     * @return {@code true} if the user inputs 'y', {@code false} if the user inputs 'n'
     */
    public static boolean readBoolean(String message) {
        while (true) {
            Application.DEFAULT_OUTPUT_STREAM.println(message);
            String answer = scanner.nextLine();
            if (!answer.isEmpty()) {
                switch (answer) {
                    case "y": return true;
                    case "n": return false;
                    default: break;
                }
            }
            Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        }
    }

    /**
     * Reads a double value from user input after displaying a specified prompt message.
     * Continues prompting until valid input is provided.
     *
     * @param message the prompt message to display to the user
     * @return the double value entered by the user
     */
    public static double readDouble(String message) {
        while (true) {
            Application.DEFAULT_OUTPUT_STREAM.println(message);
            String answer = scanner.nextLine();
            if (Pattern.matches(DOUBLE_REGEX, answer)) {
                return Double.parseDouble(answer);
            }
            Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        }
    }

    /**
     * Reads an integer value from user input after displaying a specified prompt message.
     * Continues prompting until a valid integer input is provided.
     *
     * @param message the prompt message to display to the user
     * @return the integer value entered by the user
     */
    public static int readInteger(String message) {
        while (true) {
            Application.DEFAULT_OUTPUT_STREAM.println(message);
            String answer = scanner.nextLine();
            if (Pattern.matches(INTEGER_REGEX, answer)) {
                return Integer.parseInt(answer);
            }
            Application.DEFAULT_ERROR_STREAM.println(ERROR_MESSAGE);
        }
    }

    /**
     * Sets the scanner to be used for input operations within the {@code Reader} class.
     *
     * @param scanner the {@code Scanner} instance to set for reading input
     */
    public static void setScanner(Scanner scanner) {
        Reader.scanner = scanner;
    }

}
