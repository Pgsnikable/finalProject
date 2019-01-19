package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.DrugCategory;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("Duplicates")
public class GoToDeleteCategoryCommand implements Command {
    private static final String ALL_CATEGORY = "allCategory";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = PageManager.getPageURI(PageMappingConstant.ADMIN_DELETE_CATEGORY_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        try {
            List<DrugCategory> drugCategory = DrugService.getInstance().findAllDrugCategory();
            request.setAttribute(ALL_CATEGORY, drugCategory);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
