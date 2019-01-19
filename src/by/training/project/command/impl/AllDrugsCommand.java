package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugCategory;
import by.training.project.entity.DrugDosage;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class AllDrugsCommand implements Command {
    private static final String ALL_DRUGS_MAP = "allDrugsMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = PageManager.getPageURI(PageMappingConstant.ALL_DRUGS_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);

        Map<DrugCategory, List<Map.Entry<Drug, List<DrugDosage>>>> allDrugsMap = new HashMap<>();
        try {
            List<DrugCategory> categories = DrugService.getInstance().findAllDrugCategory();
            for (DrugCategory category : categories) {
                List<Map.Entry<Drug, List<DrugDosage>>> categoryDrugs =
                        DrugService.getInstance().findAllDrugsByCategoryId(category.getCategoryId());
                allDrugsMap.put(category, categoryDrugs);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        request.setAttribute(ALL_DRUGS_MAP, allDrugsMap);
        return commandResult;
    }
}
