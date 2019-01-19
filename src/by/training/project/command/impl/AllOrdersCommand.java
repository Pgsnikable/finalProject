package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugDosage;
import by.training.project.entity.Order;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;
import by.training.project.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("Duplicates")
public class AllOrdersCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String ALL_ORDER_MAP = "allOrderMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ALL_ORDERS_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        Map<Order, Map.Entry<Drug, DrugDosage>> allOrderMap = new HashMap<>();

        try {
            List<Order> orderList = OrderService.getInstance().getOrderListByUserId(user.getUserId());
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance().findDrugDosageById(order.getDrugDosageId());
                    if (optionalDrugDosage.isPresent()) {
                        DrugDosage drugDosage = optionalDrugDosage.get();
                        Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugDosage.getDrugId());
                        if (optionalDrug.isPresent()) {
                            Drug drug = optionalDrug.get();
                            Map.Entry<Drug, DrugDosage> dosageAndDrug = new AbstractMap.SimpleEntry<>(drug, drugDosage);
                            allOrderMap.put(order, dosageAndDrug);
                        }
                    }
                }
                request.setAttribute(ALL_ORDER_MAP, allOrderMap);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
