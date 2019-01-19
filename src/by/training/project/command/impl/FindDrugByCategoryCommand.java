package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugDosage;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class FindDrugByCategoryCommand implements Command {
    private static final String CATEGORY_ID = "categoryId";
    private static final String CATEGORY_DRUG_LIST = "categoryDrugList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = PageManager.getPageURI(PageMappingConstant.ALL_ABOUT_DRUG_PAGE_KEY);
        Long categoryId = Long.valueOf(request.getParameter(CATEGORY_ID));

        try {
            List<Map.Entry<Drug, List<DrugDosage>>> drugs = DrugService.getInstance().findAllDrugsByCategoryId(categoryId);
            request.setAttribute(CATEGORY_DRUG_LIST, drugs);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return new CommandResult(page, NavigationType.FORWARD);
    }
}
