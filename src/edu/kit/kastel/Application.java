package edu.kit.kastel;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;
import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.ui.handlers.DefaultCommandHandler;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The main application class that provides the entry point for the program.
 * <p>
 * It handles command line arguments to load a configuration file and optionally set
 * a seed for the random number generator or activate debug mode. After parsing the configuration,
 * the default command handler is started to process user commands.
 * </p>
 *
 * @author uyqbd
 */
public final class Application {
    private static final String COMMAND_LINE_ARGUMENTS_MESSAGE = "Error, wrong arguments count, 1 or 2 line arguments expected.";
    private static final String WRONG_SECOND_ARGUMENT_MESSAGE = "Error, wrong second argument, number or 'debug' expected.";
    private static final String ERROR_WRONG_NUMBER_FORMAT = "Error, seed has to be between %d and %d".formatted(
            Long.MIN_VALUE,
            Long.MAX_VALUE
    );

    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    private static final Scanner SCANNER = new Scanner(DEFAULT_INPUT_STREAM);

    private static DefaultCommandHandler commandHandler;

    private Application() {

    }

    /**
     * The entry point for the application. No command line arguments are expected.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println(COMMAND_LINE_ARGUMENTS_MESSAGE);
            return;
        }

        if (handleArguments(args)) {
            commandHandler = new DefaultCommandHandler();
            commandHandler.startHandling();
        }

        SCANNER.close();
    }

    private static boolean handleArguments(String[] args) {
        try {
            ConfigParser.parse(args[0]);
        } catch (ConfigPatternException e) {
            System.err.println(e.getMessage());
            return false;
        }

        if (args.length == 2) {
            if (Pattern.matches("\\d+", args[1])) {
                try {
                    RandomGenerator.setSeed(Long.parseLong(args[1]));
                } catch (NumberFormatException e) {
                    System.err.println(ERROR_WRONG_NUMBER_FORMAT);
                }
            } else if (args[1].equals("debug")) {
                RandomGenerator.switchDebugMod();
            } else {
                System.err.println(WRONG_SECOND_ARGUMENT_MESSAGE);
                return false;
            }
        }

        return true;
    }

    /**
     * Reads a line of input from scanner.
     *
     * @return the next line entered by the user
     */
    public static String readInputLine() {
        return SCANNER.nextLine();
    }

    /**
     * Stops the command handler, effectively terminating the application.
     */
    public static void stop() {
        commandHandler.stop();
    }

}
