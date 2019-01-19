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

import javax.servlet.http.HttpServletRequest;

public class AddDosageCommand implements Command {
    private static final String DRUG_ID = "drugId";
    private static final String DOSAGE_NAME = "dosageName";
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "addDosageSuccess";
    private static final String IF_ERROR_MESSAGE = "addDosageFailed";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);

        Long drugId = Long.valueOf(request.getParameter(DRUG_ID));
        String dosageName = request.getParameter(DOSAGE_NAME);
        try {
            if (DrugService.getInstance().addDrugDosage(drugId, dosageName)) {
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
