package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserPaymentData;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class AddBalanceCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String SESSION_USER_PAYMENT_DATA = "userPaymentData";
    private static final BigDecimal BALANCE_FOR_EVERYONE = new BigDecimal(50);
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "addBalanceSuccess";
    private static final String IF_ERROR_MESSAGE = "addBalanceError";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        try {
            if (UserService.getInstance().addBalance(BALANCE_FOR_EVERYONE, user.getUserId())) {
                Optional<UserPaymentData> optionalPaymentData = UserService.getInstance().getPaymentDataById(user.getUserId());
                if (optionalPaymentData.isPresent()) {
                    UserPaymentData updatedPaymentData = optionalPaymentData.get();
                    request.getSession().setAttribute(SESSION_USER_PAYMENT_DATA, updatedPaymentData);
                    request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                }
            } else {
                request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}