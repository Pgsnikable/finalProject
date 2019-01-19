package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserRole;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class DeleteUserCommand implements Command {
    private static final boolean FLAG = true;
    private static final String IF_SUCCESS_MESSAGE = "userDeleteSuccess";
    private static final String IF_ERROR_MESSAGE = "userDeleteFailed";
    private static final String DELETED_USER_ID = "userId";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.REDIRECT);
        Long userId = Long.valueOf(request.getParameter(DELETED_USER_ID));
        try {
            Optional<UserWithoutPassword> optionalUser = UserService.getInstance().findUserWithoutPasswordById(userId);
            if (optionalUser.isPresent()) {
                UserWithoutPassword user = optionalUser.get();
                if (UserRole.USER == user.getRole()) {
                    if (UserService.getInstance().deleteUser(userId)) {
                        request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                    } else {
                        request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                    }
                } else {
                    if (UserService.getInstance().deleteAdminOrDoctor(userId)) {
                        request.getSession().setAttribute(IF_SUCCESS_MESSAGE, FLAG);
                    } else {
                        request.getSession().setAttribute(IF_ERROR_MESSAGE, FLAG);
                    }
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Service layer error", e);
        }
        return commandResult;
    }
}
