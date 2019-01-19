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

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DeclineOrderCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "orderDeleteMessageSuccess";
    private static final String IF_ERROR_MESSAGE = "orderDeleteMessageError";
    private static final String SESSION_USER = "user";
    private static final String DECLINED_ORDER_ID = "declinedOrderId";
    private static final String SESSION_USER_PAYMENT_DATA = "userPaymentData";
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = "";
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        Long declinedOrderId = Long.valueOf(request.getParameter(DECLINED_ORDER_ID));

        try {
            Optional<Order> optionalOrder = OrderService.getInstance().findOrderById(declinedOrderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance().findDrugDosageById(order.getDrugDosageId());
                if (optionalDrugDosage.isPresent()) {
                    DrugDosage drugDosage = optionalDrugDosage.get();
                    if (OrderOrRecipeStatus.WAITING == order.getStatus() && UserRole.USER == user.getRole()) {
                        page = PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
                        try {
                            if (OrderService.getInstance().deleteOrder(declinedOrderId) &&
                                    DrugService.getInstance().returnDrugToStorageQuantity(order.getQuantity(), drugDosage.getDrugId())) {
                                UserPaymentData userPaymentData = OrderService.getInstance().returnOrderCost(order.getOrderCost(), order.getUserId());
                                request.getSession().setAttribute(SESSION_USER_PAYMENT_DATA, userPaymentData);
                                request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                            } else {
                                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                            }
                        } catch (ServiceException e) {
                            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
                        }
                    } else if (UserRole.ADMIN == user.getRole()) {
                        page = PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
                        if (OrderService.getInstance().deleteOrder(declinedOrderId) &&
                                DrugService.getInstance().returnDrugToStorageQuantity(order.getQuantity(), drugDosage.getDrugId())) {
                            OrderService.getInstance().returnOrderCost(order.getOrderCost(), order.getUserId());
                            request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                        } else {
                            request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                        }

                    } else {
                        request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                    }
                } else {
                    request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                }
            } else {
                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return new CommandResult(page, NavigationType.REDIRECT);
    }
}
