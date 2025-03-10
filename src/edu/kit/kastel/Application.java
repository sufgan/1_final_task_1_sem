package edu.kit.kastel;

import edu.kit.kastel.config.ConfigParser;
import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.ui.handlers.DefaultCommandHandler;

import java.io.InputStream;
import java.io.PrintStream;
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
    private static final String DEBUG_MODE_FLAG = "debug";
    private static final String ERROR_WRONG_NUMBER_FORMAT = "Error, seed has to be between %d and %d".formatted(
            Long.MIN_VALUE,
            Long.MAX_VALUE
    );

    private static final int ARGS_COUNT = 1;
    private static final int ARGS_COUNT_WITH_RANDOM = 2;
    private static final int CONFIG_PATH_INDEX = 0;
    private static final int RANDOM_INDEX = 1;
    private static final String SEED_REGEX = "-?\\d+";

    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    private static final PrintStream DEFAULT_OUTPUT_STREAM = System.out;
    private static final PrintStream DEFAULT_ERROR_STREAM = System.err;
    private static final Scanner SCANNER = new Scanner(DEFAULT_INPUT_STREAM);

    private Application() {

    }

    /**
     * The entry point for the application. No command line arguments are expected.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != ARGS_COUNT && args.length != ARGS_COUNT_WITH_RANDOM) {
            System.err.println(COMMAND_LINE_ARGUMENTS_MESSAGE);
            return;
        }

        try (Scanner scanner = new Scanner(DEFAULT_INPUT_STREAM)) {
            handleArguments(args);
            new DefaultCommandHandler(scanner).startHandling();
        } catch (ApplicationException e) {
            DEFAULT_ERROR_STREAM.println(e.getMessage());
        }

        SCANNER.close();
    }

    private static void handleArguments(String[] args) throws ApplicationException {
        ConfigParser.parse(args[CONFIG_PATH_INDEX]);

        if (args.length == ARGS_COUNT_WITH_RANDOM) {
            parseRandom(args[RANDOM_INDEX]);
        }
    }

    private static void parseRandom(String rawRandom) throws ApplicationException {
        if (Pattern.matches(SEED_REGEX, rawRandom)) {
            parseSeed(rawRandom);
        } else if (rawRandom.equals(DEBUG_MODE_FLAG)) {
            RandomGenerator.switchDebugMod();
        } else {
            throw new ApplicationException(WRONG_SECOND_ARGUMENT_MESSAGE);
        }
    }

    private static void parseSeed(String seed) throws ApplicationException {
        try {
            RandomGenerator.setSeed(Long.parseLong(seed));
        } catch (NumberFormatException e) {
            throw new ApplicationException(ERROR_WRONG_NUMBER_FORMAT);
        }
    }

    /**
     * Reads a line of input from scanner.
     *
     * @return the next line entered by the user
     */
    public static String readInputLine() {
        return SCANNER.nextLine();
    }

}
