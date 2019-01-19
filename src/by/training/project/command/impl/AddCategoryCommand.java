package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;
import by.training.project.validator.DrugValidator;

import javax.servlet.http.HttpServletRequest;

public class AddCategoryCommand implements Command {
    private static final String CATEGORY_NAME = "categoryName";
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "addCategorySuccess";
    private static final String IF_ERROR_MESSAGE = "addCategoryFailed";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        String categoryName = request.getParameter(CATEGORY_NAME);
        try {
            if (DrugValidator.isValidCategoryName(categoryName) && DrugService.getInstance().addCategory(categoryName)) {
                request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
            } else {
                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
