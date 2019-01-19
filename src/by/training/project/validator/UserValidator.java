package by.training.project.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserValidator {
    private static final String LOGIN_REG_EXP = "^[\\w-]{4,31}$";
    private static final String PASSWORD_REG_EXP = "^[A-Za-z]\\w{3,31}$";

    /**
     * Validate user's login value
     *
     * @param login its user's login value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidLogin(String login) {
        return login != null && login.matches(LOGIN_REG_EXP);
    }

    /**
     * Validate user's encoded password value
     *
     * @param password its a encoded user's password value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REG_EXP);
    }
}
