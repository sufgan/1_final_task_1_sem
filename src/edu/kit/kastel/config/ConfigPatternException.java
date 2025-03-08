package edu.kit.kastel.config;

/**
 * Thrown when the configuration has an incorrect pattern.
 *
 * <p>Use this exception to indicate invalid config data.</p>
 *
 * @author uyqbd
 */
public class ConfigPatternException extends Exception {
    private static final String MESSAGE = "Error, ";

    /**
     * Constructs a {@code ConfigPatternException} with a detailed error message.
     * This exception is thrown when configuration data does not adhere to the
     * expected format or pattern.
     *
     * @param message a {@code String} specifying the details of the error.
     *                This message will be appended to a predefined prefix
     *                indicating a configuration error.
     */
    public ConfigPatternException(String message) {
        super(MESSAGE + message);
    }

}
