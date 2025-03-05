package edu.kit.kastel.ui;

import edu.kit.kastel.Application;
import edu.kit.kastel.ui.commands.Command;
import edu.kit.kastel.ui.commands.CommandException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommandHandler {
    private static final String ERROR_UNKNOWN_COMMAND_FORMAT = "unknown command or count/type of arguments: %s";

    private final Map<String, Command> commands;
    private boolean running;

    protected CommandHandler(Command... commands) {
        this.commands = new HashMap<>();
        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }
    }

    public void startHandling() {
        running = true;
        while (running) {
            try {
                handleCommand(Application.readInputLine());
            } catch (Exception e) { // CommandException | ConfigPatternException
                System.err.println(e.getMessage());
//                e.printStackTrace();
            }
        }
        // TODO add config didn't found exception
    }

    public void handleCommand(String line) throws CommandException {
//        Matcher matcher = Pattern.compile(getCommandsRegex()).matcher(line); // recreate for competition's 'action...'
        for (Command command : commands.values()) {
            Matcher matcher = Pattern.compile(command.toRegex()).matcher(line);
            if (matcher.find()) {
                String[] args = matcher.group(1).split(Command.SEPARATOR);
                command.execute(args);
                return;
            }
        }
        throw new CommandException(String.format(ERROR_UNKNOWN_COMMAND_FORMAT, line));
    }

//    private String getCommandsRegex() {
//        String[] regexes = new String[commands.size()];
//        int i = 0;
//        for (Command command : commands.values()) {
//            regexes[i++] = command.toRegex();
//        }
//        return RegexConstructor.groupOR("", regexes);
//    }

    public void stop() {
        running = false;
    }

}
