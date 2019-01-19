package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.DrugDosage;
import by.training.project.entity.Order;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.DrugService;
import by.training.project.service.OrderService;
import by.training.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class ChangeOrderStatusCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "orderSuccess";
    private static final String IF_ERROR_MESSAGE = "orderFailed";
    private static final String ORDER_ID = "orderId";
    private static final String CHANGED_STATUS = "changedStatus";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        Long orderId = Long.valueOf(request.getParameter(ORDER_ID));
        OrderOrRecipeStatus status = OrderOrRecipeStatus.valueOf(request.getParameter(CHANGED_STATUS));
        switch (status) {
            case WAITING:
                changeOrderStatus(request, orderId, status);
                break;
            case DECLINED:
                try {
                    Optional<Order> optionalOrder = OrderService.getInstance().findOrderById(orderId);
                    if (optionalOrder.isPresent()) {
                        Order order = optionalOrder.get();
                        Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance().findDrugDosageById(order.getDrugDosageId());
                        if (optionalDrugDosage.isPresent()) {
                            DrugDosage drugDosage = optionalDrugDosage.get();
                            if (DrugService.getInstance()
                                    .returnDrugToStorageQuantity(order.getQuantity(), drugDosage.getDrugId()) &&
                                    OrderService.getInstance().updateOrderStatus(orderId, status)) {
                                OrderService.getInstance().returnOrderCost(order.getOrderCost(), order.getUserId());
                                request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                            } else {
                                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                            }
                        }
                    }
                } catch (ServiceException e) {
                    throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
                }
                break;
            case ACCEPTED:
                changeOrderStatus(request, orderId, status);
                break;
            default:
                throw new EnumConstantNotPresentException(OrderOrRecipeStatus.class, status.name());
        }
        return commandResult;
    }

    private void changeOrderStatus(HttpServletRequest request, Long orderId, OrderOrRecipeStatus status) throws CommandException {
        try {
            if (OrderService.getInstance().updateOrderStatus(orderId, status)) {
                request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
            } else {
                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
    }
}
