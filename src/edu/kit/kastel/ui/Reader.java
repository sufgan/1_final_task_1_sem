package edu.kit.kastel.ui;

import edu.kit.kastel.Application;

import java.util.regex.Pattern;

public class Reader {

    public static boolean readBoolean(String message) {
        System.out.println(message);
        String answer = Application.readInputLine();
        if (answer.length() == 1) {
            switch (answer.charAt(0)) {
                case 'y': return true;
                case 'n': return false;
            }
        }
        System.out.println("Error, enter y or n.");
        return readBoolean(message);
    }

    public static double readDouble(String message) {
        System.out.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches("\\d+\\.\\d+", answer)) {
            return Double.parseDouble(answer);
        }
        System.out.println("Error, wrong value.");
        return readDouble(message);
    }

    public static int readInteger(String message) {
        System.out.println(message);
        String answer = Application.readInputLine();
        if (Pattern.matches("\\d+", answer)) {
            return Integer.parseInt(answer);
        }
        System.err.println("Error, wrong value.");
        return readInteger(message);
    }

}
