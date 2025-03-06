package edu.kit.kastel.config;

/**
 * Thrown when the configuration has an incorrect pattern.
 *
 * <p>Use this exception to indicate invalid config data.</p>
 *
 * @author uyqbd
 */
public class ConfigPatternException extends RuntimeException {
    private static final String MESSAGE = "Config error, wrong pattern";

    /**
     * Constructs a new {@code ConfigPatternException} with a default message.
     */
    public ConfigPatternException() {
        super(MESSAGE);
    }

}
