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

public class AllCategoriesCommand implements Command {
    private static final String CATEGORY_LIST = "categoryList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        List<DrugCategory> categoryList;
        String page = PageManager.getPageURI(PageMappingConstant.ALL_ABOUT_DRUG_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        try {
            categoryList = DrugService.getInstance().findAllDrugCategory();
            request.setAttribute(CATEGORY_LIST, categoryList);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
