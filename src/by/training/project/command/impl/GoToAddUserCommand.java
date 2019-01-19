package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserRole;
import by.training.project.exception.CommandException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;

import javax.servlet.http.HttpServletRequest;

public class GoToAddUserCommand implements Command {
    private static final String ROLES = "roles";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_ADD_USER_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        request.setAttribute(ROLES, UserRole.values());
        return commandResult;
    }
}
