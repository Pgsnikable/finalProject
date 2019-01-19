package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugDosage;
import by.training.project.entity.Recipe;
import by.training.project.entity.UserWithoutPassword;
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
public class AllRecipesCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String ALL_RECIPE_MAP = "allRecipeMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ALL_RECIPES_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);

        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        Map<Recipe, Map.Entry<Drug, DrugDosage>> allRecipeMap = new HashMap<>();
        try {
            List<Recipe> recipeList = RecipeService.getInstance().findAllRecipesByUserId(user.getUserId());
            if (!recipeList.isEmpty()) {
                for (Recipe recipe : recipeList) {
                    Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance().findDrugDosageById(recipe.getDrugDosageId());
                    if (optionalDrugDosage.isPresent()) {
                        DrugDosage drugDosage = optionalDrugDosage.get();
                        Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugDosage.getDrugId());
                        if (optionalDrug.isPresent()) {
                            Drug drug = optionalDrug.get();
                            Map.Entry<Drug, DrugDosage> dosageAndDrug = new AbstractMap.SimpleEntry<>(drug, drugDosage);
                            allRecipeMap.put(recipe, dosageAndDrug);
                        }
                    }
                }
                request.setAttribute(ALL_RECIPE_MAP, allRecipeMap);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}