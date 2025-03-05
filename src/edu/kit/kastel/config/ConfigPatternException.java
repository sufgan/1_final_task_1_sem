package edu.kit.kastel.config;

public class ConfigPatternException extends RuntimeException {
    private static final String MESSAGE = "Config error, wrong pattern";

    public ConfigPatternException() {
        super(MESSAGE);
    }
}
