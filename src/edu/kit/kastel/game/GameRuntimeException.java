package edu.kit.kastel.game;

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
public class GameRuntimeException extends Exception {
    private static final String MESSAGE_PREFIX = "Game error, ";

    /**
     * Constructs a new {@code GameRuntimeException} with a detailed error message,
     * prefixed with a predefined constant message.
     *
     * @param message the specific error message to be included with the exception
     */
    public GameRuntimeException(String message) {
        super(MESSAGE_PREFIX + message);
    }
}
