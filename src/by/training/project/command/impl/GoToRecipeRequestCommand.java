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

public class GoToRecipeRequestCommand implements Command {
    private static final String DRUGS_MAP = "drugsMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page;
        CommandResult commandResult;
        try {
            List<Map.Entry<Drug, List<DrugDosage>>> drugs = DrugService.getInstance().findAllRecipeNedeedDrugs();
            request.setAttribute(DRUGS_MAP, drugs);
            page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.RECIPE_REQUEST_FORM_PAGE_KEY);
            commandResult = new CommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
