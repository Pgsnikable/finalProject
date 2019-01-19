package by.training.project.command;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandFactory {
    /**
     * Its a one method in this utility class which
     * define {@code Command} value in enum's by her
     * {@code String} representation
     *
     * @param commandName its a {@code String} representation
     *                    of command name
     * @return {@code Command} object from current enum
     * @throws IllegalArgumentException if the specified enum
     *                                  type has no constant with the specified name, or the
     *                                  specified class object does not represent an enum type
     */
    public Command defineCommand(String commandName) {
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        return commandType.getCommand();
    }
}
