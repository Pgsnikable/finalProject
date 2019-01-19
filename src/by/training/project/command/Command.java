package by.training.project.command;

import by.training.project.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    /**
     * Execute current {@code Command}
     *
     * @param request its a {@code HttpServletRequest} for execute current command
     * @return {@code CommandResult} which contains result page and navigation type
     * @throws CommandException if Service layer doesnt work correct
     */
    CommandResult execute(HttpServletRequest request) throws CommandException;
}
