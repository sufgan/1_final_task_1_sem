package edu.kit.kastel.game;

public class GameRuntimeException extends RuntimeException {
    public static String MESSAGE_PREFIX = "Game error, ";

    public GameRuntimeException(String message) {
        super(MESSAGE_PREFIX + message);
    }
}
