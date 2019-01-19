package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserRole;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String CONTROLLER_URL = "http://localhost:8080/mainController";
    private static final String LANGUAGE = "language";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String controllerPage = CONTROLLER_URL;
        String referer = request.getHeader("referer");
        String page = "";
        NavigationType navigationType = NavigationType.FORWARD;
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        if (controllerPage.equals(referer)) {
            if (user.getRole() == UserRole.USER) {
                page = PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
            } else if (user.getRole() == UserRole.ADMIN) {
                page = PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
            } else if (user.getRole() == UserRole.DOCTOR) {
                page = PageManager.getPageURI(PageMappingConstant.DOCTOR_MAIN_PAGE_KEY);
            }
        } else {
            page = referer;
            navigationType = NavigationType.REDIRECT;
        }
        String language = request.getParameter(LANGUAGE);
        request.setAttribute(LANGUAGE, language);
        return new CommandResult(page, navigationType);
    }
}
