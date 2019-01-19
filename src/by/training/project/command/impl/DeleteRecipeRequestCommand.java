package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.RecipeService;

import javax.servlet.http.HttpServletRequest;

public class DeleteRecipeRequestCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "requestDeleteSuccess";
    private static final String IF_ERROR_MESSAGE = "requestDeleteError";
    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_STATUS = "requestStatus";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        Long requestId = Long.valueOf(request.getParameter(REQUEST_ID));
        OrderOrRecipeStatus status = OrderOrRecipeStatus.valueOf(request.getParameter(REQUEST_STATUS).toUpperCase());
        try {
            if (OrderOrRecipeStatus.WAITING == status && RecipeService.getInstance().deleteRecipeRequest(requestId)) {
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
