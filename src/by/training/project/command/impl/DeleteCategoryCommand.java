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

@SuppressWarnings("Duplicates")
public class DeleteCategoryCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "categoryDeleteSuccess";
    private static final String IF_ERROR_MESSAGE = "categoryDeleteFailed";
    private static final String CATEGORY_ID = "categoryId";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        Long categoryId = Long.valueOf(request.getParameter(CATEGORY_ID));
        try {
            List<Map.Entry<Drug, List<DrugDosage>>> categoryDrugs = DrugService.getInstance().findAllDrugsByCategoryId(categoryId);
            for (Map.Entry<Drug, List<DrugDosage>> drugMap : categoryDrugs) {
                DrugService.getInstance().deleteDrug(drugMap.getKey().getDrugId());
            }
            if (DrugService.getInstance().deleteCategory(categoryId)) {
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
