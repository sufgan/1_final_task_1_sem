package edu.kit.kastel;

import edu.kit.kastel.config.ConfigPatternException;
import edu.kit.kastel.config.ConfigParser;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.DefaultCommandHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The class offering the entry point for the application.
 *
 * @author i
 */
public final class Application {
    private static final String COMMAND_LINE_ARGUMENTS_MESSAGE = "Wrong arguments count, 1 or 2 line arguments expected.";
    private static final String WRONG_FIRST_ARGUMENT_MESSAGE = "Wrong first argument, path doesn't exist.";
    private static final String WRONG_SECOND_ARGUMENT_MESSAGE = "Wrong second argument, number or 'debug' expected.";
    private static final String ERROR_WRONG_NUMBER_FORMAT = "Seed has to be between %d and %d".formatted(Long.MIN_VALUE, Long.MAX_VALUE);

    private static final InputStream DEFAULT_INPUT_STREAM = System.in;
    private static final Scanner SCANNER = new Scanner(DEFAULT_INPUT_STREAM);

    private static CommandHandler commandHandler;

    private Application() {
        // utility class
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
        // TODO check args count 1
        Path path = Paths.get(args[0]);
        if (Files.notExists(path)) {
            System.err.println(WRONG_FIRST_ARGUMENT_MESSAGE);
            return false;
        }
        try {
            ConfigParser.parse(Files.readString(path));
        } catch (ConfigPatternException e) {
            System.err.printf(e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.printf(WRONG_FIRST_ARGUMENT_MESSAGE);
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

    public static String readInputLine() {
        return SCANNER.nextLine();
    }

    public static void stop() {
        commandHandler.stop();
    }

}
