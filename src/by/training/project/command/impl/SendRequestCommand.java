package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.entity.RecipeRequest;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.RecipeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@SuppressWarnings("Duplicates")
public class SendRequestCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "requestSuccess";
    private static final String IF_ERROR_MESSAGE = "requestError";
    private static final String SESSION_USER = "user";
    private static final String DRUG_DOSAGE_ID = "drugDosageId";
    private static final String EXPIRATION_DATE = "expirationDate";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);

        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        Long drugDosageId = Long.valueOf(request.getParameter(DRUG_DOSAGE_ID));
        LocalDate expirationDate = LocalDate.parse(request.getParameter(EXPIRATION_DATE));
        OrderOrRecipeStatus status = OrderOrRecipeStatus.WAITING;
        RecipeRequest recipeRequest = RecipeService.getInstance().makeRequest(user.getUserId(), drugDosageId, expirationDate, status);
        try {
            if (RecipeService.getInstance().sendRecipeRequest(recipeRequest)) {
                request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
            } else {
                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
            }
        } catch (ServiceException e) {
            throw new CommandException("Service layer error", e);
        }
        return commandResult;
    }
}
