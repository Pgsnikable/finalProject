package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserData;
import by.training.project.entity.UserPaymentData;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.UserService;
import by.training.project.util.PasswordEncoder;
import by.training.project.validator.UserDataValidator;
import by.training.project.validator.UserPaymentDataValidator;
import by.training.project.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@SuppressWarnings("Duplicates")
public class UpdateUserCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String SESSION_USER = "user";
    private static final String SESSION_USER_DATA = "userData";
    private static final String SESSION_USER_PAYMENT_DATA = "userPaymentData";
    private static final boolean FLAG = true;
    private static final String IF_ERROR_MESSAGE = "updatedFailed";
    private static final String IF_SUCCESS_MESSAGE = "updatedSuccess";


    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        String loginValue = request.getParameter(LOGIN);
        String passwordValue = request.getParameter(PASSWORD);
        String firstNameValue = request.getParameter(FIRST_NAME);
        String lastNameValue = request.getParameter(LAST_NAME);
        String emailValue = request.getParameter(EMAIL);
        String birthdayValue = request.getParameter(BIRTHDAY);
        String cardNumber = request.getParameter(CARD_NUMBER);

        UserWithoutPassword user = (UserWithoutPassword) request.getSession().getAttribute(SESSION_USER);
        UserData sessionUserData = (UserData) request.getSession().getAttribute(SESSION_USER_DATA);

        try {
            if (UserValidator.isValidLogin(loginValue) &&
                    UserValidator.isValidPassword(passwordValue) &&
                    UserDataValidator.isValidFirstName(firstNameValue) &&
                    UserDataValidator.isValidLastName(lastNameValue) &&
                    UserDataValidator.isValidEmail(emailValue) &&
                    UserDataValidator.isValidBirthday(birthdayValue) &&
                    UserPaymentDataValidator.isValidCardNumber(cardNumber) &&
                    UserService.getInstance().isUserExist(loginValue, PasswordEncoder.encodePassword(passwordValue)) &&
                    (!UserService.getInstance().isEmailExist(emailValue) || sessionUserData.getEmail().equals(emailValue))) {
                UserData updatedUserData = UserService.getInstance()
                        .makeUserData(firstNameValue, lastNameValue, emailValue, LocalDate.parse(birthdayValue));
                UserPaymentData updatedPaymentData = UserService.getInstance().makePaymentData(Long.valueOf(cardNumber));
                if (UserService.getInstance().updateUserData(user, updatedUserData, updatedPaymentData)) {
                    request.getSession().setAttribute(SESSION_USER_DATA, updatedUserData);
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
