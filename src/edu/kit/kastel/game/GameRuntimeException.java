package edu.kit.kastel.game;

import edu.kit.kastel.ApplicationException;

/**
 * A custom runtime exception used to indicate errors specifically related to the game logic.
 * This exception appends a predefined prefix to the provided error message.
 *
 * <p>
 * Example uses in the game logic include enforcing game rules or constraints,
 * such as ensuring an action has a valid target.
 * </p>
 *
 * @author uyqbd
 */
public class GameRuntimeException extends ApplicationException {

    /**
     * Constructs a new {@code GameRuntimeException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public GameRuntimeException(String message) {
        super(message);
    }
}
