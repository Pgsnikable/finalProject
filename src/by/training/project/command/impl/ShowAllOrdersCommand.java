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
import by.training.project.service.OrderService;
import by.training.project.service.RecipeService;
import by.training.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("Duplicates")
public class ShowAllOrdersCommand implements Command {
    private static final String ALL_STATUS = "allStatus";
    private static final String ORDER_MAP = "orderMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_ALL_ORDERS_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);
        Map<Map.Entry<UserData, Order>, Map.Entry<Drug, DrugDosage>> allUserOrdersMap = new HashMap<>();

        try {
            List<Order> orderList = OrderService.getInstance().findAllOrders();
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    Optional<UserWithoutPassword> optionalUser = UserService.getInstance()
                            .findUserWithoutPasswordById(order.getUserId());
                    if (optionalUser.isPresent()) {
                        UserWithoutPassword user = optionalUser.get();
                        Optional<UserData> optionalUserData = UserService.getInstance().findUserDataByUserId(user.getUserId());
                        if (optionalUserData.isPresent()) {
                            UserData userData = optionalUserData.get();
                            Map.Entry<UserData, Order> userDataAndOrder = new AbstractMap.SimpleEntry<>(userData, order);
                            Optional<DrugDosage> optionalDosage = DrugService.getInstance().findDrugDosageById(order.getDrugDosageId());
                            if (optionalDosage.isPresent()) {
                                DrugDosage drugDosage = optionalDosage.get();
                                Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugDosage.getDrugId());
                                if (optionalDrug.isPresent()) {
                                    Drug drug = optionalDrug.get();
                                    Map.Entry<Drug, DrugDosage> drugAndDosage = new AbstractMap.SimpleEntry<>(drug, drugDosage);
                                    allUserOrdersMap.put(userDataAndOrder, drugAndDosage);
                                }
                            }
                        }
                    }
                }
                request.setAttribute(ALL_STATUS, OrderOrRecipeStatus.values());
                request.setAttribute(ORDER_MAP, allUserOrdersMap);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
