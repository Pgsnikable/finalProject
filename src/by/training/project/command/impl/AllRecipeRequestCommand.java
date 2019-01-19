package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.*;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;
import by.training.project.service.RecipeService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("Duplicates")
public class AllRecipeRequestCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String ALL_REQUSET_MAP = "allRequestMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ALL_RECIPE_REQUEST_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        Map<RecipeRequest, Map.Entry<Drug, DrugDosage>> allRequestMap = new HashMap<>();
        try {
            List<RecipeRequest> requestList = RecipeService.getInstance().findAllRecipeRequestByUserId(user.getUserId());
            if (!requestList.isEmpty()) {
                for (RecipeRequest recipeRequest : requestList) {
                    Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance()
                            .findDrugDosageById(recipeRequest.getDrugDosageId());
                    if (optionalDrugDosage.isPresent()) {
                        DrugDosage drugDosage = optionalDrugDosage.get();
                        Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugDosage.getDrugId());
                        if (optionalDrug.isPresent()) {
                            Drug drug = optionalDrug.get();
                            Map.Entry<Drug, DrugDosage> drugAndDosage = new AbstractMap.SimpleEntry<>(drug, drugDosage);
                            allRequestMap.put(recipeRequest, drugAndDosage);
                        }
                    }
                }
                request.setAttribute(ALL_REQUSET_MAP, allRequestMap);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}