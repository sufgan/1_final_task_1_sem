package edu.kit.kastel.config;

import edu.kit.kastel.ApplicationException;

/**
 * Thrown when the configuration has an incorrect pattern.
 *
 * <p>Use this exception to indicate invalid config data.</p>
 *
 * @author uyqbd
 */
public class ConfigPatternException extends ApplicationException {

    /**
     * Constructs a ConfigPatternException with a specified error message.
     *
     * @param message the detailed error message describing the configuration pattern violation
     */
    public ConfigPatternException(String message) {
        super(message);
    }

}
