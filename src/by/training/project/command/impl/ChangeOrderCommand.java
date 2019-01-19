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
import by.training.project.service.UserService;
import by.training.project.validator.OrderValidator;
import by.training.project.validator.UserPaymentDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ChangeOrderCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "orderChangeMessageSuccess";
    private static final String IF_ERROR_MESSAGE = "orderChangeMessageError";
    private static final String SESSION_USER = "user";
    private static final String SESSION_USER_PAYMENT_DATA = "userPaymentData";
    private static final String DRUG_ID = "drugId";
    private static final String DRUG_DOSAGE_ID = "drugDosageId";
    private static final String SELECTED_QUANTITY = "selectedQuantity";
    private static final String OLD_ORDER_ID = "oldOrderId";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);

        Long drugDosageId = Long.valueOf(request.getParameter(DRUG_DOSAGE_ID));
        Long drugId = Long.valueOf(request.getParameter(DRUG_ID));
        Integer newSelectedQuantity = Integer.valueOf(request.getParameter(SELECTED_QUANTITY));
        Long oldOrderId = Long.valueOf(request.getParameter(OLD_ORDER_ID));
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        UserPaymentData userPaymentData = (UserPaymentData) request.getSession().getAttribute(SESSION_USER_PAYMENT_DATA);

        try {
            Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugId);
            Optional<Order> optionalOrder = OrderService.getInstance().findOrderById(oldOrderId);
            if (optionalDrug.isPresent() && optionalOrder.isPresent()) {
                Drug drug = optionalDrug.get();
                Order oldOrder = optionalOrder.get();
                BigDecimal newTotalPrice = drug.getPrice().multiply(new BigDecimal(newSelectedQuantity));
                if (newSelectedQuantity > 0 &&
                        OrderValidator.isEnoughDrug(newSelectedQuantity, drugId) &&
                        UserPaymentDataValidator.isEnoughBalance(newTotalPrice, userPaymentData)) {
                    Order newOrder =
                            OrderService.getInstance()
                                    .makeOrder(user.getUserId(), drugDosageId, LocalDateTime.now(),
                                            newSelectedQuantity, newTotalPrice, OrderOrRecipeStatus.WAITING);
                    newOrder.setOrderId(oldOrderId);
                    if (OrderService.getInstance().updateOrderByUserChanges(oldOrder, newOrder)) {
                        Optional<UserPaymentData> optionalPaymentData = UserService.getInstance().getPaymentDataById(user.getUserId());
                        if (optionalPaymentData.isPresent()) {
                            UserPaymentData paymentData = optionalPaymentData.get();
                            request.getSession().setAttribute(SESSION_USER_PAYMENT_DATA, paymentData);
                            request.setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                        } else {
                            request.setAttribute(IF_ERROR_MESSAGE, FLAG);
                        }
                    } else {
                        request.setAttribute(IF_ERROR_MESSAGE, FLAG);
                    }
                } else {
                    request.setAttribute(IF_ERROR_MESSAGE, FLAG);
                }
            } else {
                request.setAttribute(IF_ERROR_MESSAGE, FLAG);
            }

        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
