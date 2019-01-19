package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.exception.CommandException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        request.getSession().invalidate();
        String page = PageManager.getPageURI(PageMappingConstant.LOGIN_PAGE_KEY);
        return new CommandResult(page, NavigationType.REDIRECT);
    }
}
