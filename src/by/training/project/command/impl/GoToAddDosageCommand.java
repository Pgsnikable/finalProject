package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Drug;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToAddDosageCommand implements Command {
    private static final String DRUGS = "drugs";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_ADD_DOSAGE_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        try {
            List<Drug> drugList = DrugService.getInstance().findAllDrugs();
            request.setAttribute(DRUGS, drugList);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
