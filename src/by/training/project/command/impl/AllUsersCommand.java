package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.UserData;
import by.training.project.entity.UserRole;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.CommandException;
import by.training.project.exception.ExceptionMessage;
import by.training.project.exception.ServiceException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;
import by.training.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AllUsersCommand implements Command {
    private static final String ALL_USERS_MAP = "allUsersMap";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = request.getContextPath() + PageManager.getPageURI(PageMappingConstant.ADMIN_ALL_USERS_PAGE_KEY);
        CommandResult commandResult = new CommandResult(page, NavigationType.FORWARD);

        Map<UserWithoutPassword, UserData> allUsersMap = new HashMap<>();
        try {
            List<UserWithoutPassword> allUsers = UserService.getInstance().findAllUsers();
            for (UserWithoutPassword user : allUsers) {
                if (UserRole.USER == user.getRole()) {
                    Optional<UserData> optionalUserData = UserService.getInstance().findUserDataByUserId(user.getUserId());
                    if (optionalUserData.isPresent()) {
                        UserData userData = optionalUserData.get();
                        allUsersMap.put(user, userData);
                    }
                } else {
                    allUsersMap.put(user, UserData.builder().build());
                }
            }
            request.setAttribute(ALL_USERS_MAP, allUsersMap);
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return commandResult;
    }
}
