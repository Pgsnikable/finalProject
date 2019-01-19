package by.training.project.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserDataValidator {
    private static final String FIRST_NAME_REG_EXP = "^[A-Z][a-z]{1,16}$|^[А-Я][а-яёьъ]{1,16}$";
    private static final String LAST_NAME_REG_EXP = "^[A-Z][a-z]{1,16}$|^[А-Я][а-яёьъ]{1,16}$";
    private static final String EMAIL_REG_EXP = "^([\\w-.]{2,14})@((?:[\\w-]{0,4}\\.)?\\w[\\w-]{0,4})\\.([a-z]{2,6})$";
    // YYYY-MM-DD
    private static final String BIRTHDAY_REG_EXP = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2[0-8]|29(?=-\\d\\d-(?!1[01345789]00|2[1235679]00)\\d\\d(?:[02468][048]|[13579][26]))|30(?!-02)|31(?=-0[13578]|-1[02]))$";

    /**
     * Validate user's first name value
     *
     * @param firstName its a first name value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidFirstName(String firstName) {
        return firstName != null && firstName.matches(FIRST_NAME_REG_EXP);
    }

    /**
     * Validate user's last name value
     *
     * @param lastName its a user's last name value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidLastName(String lastName) {
        return lastName != null && lastName.matches(LAST_NAME_REG_EXP);
    }

    /**
     * Validate user's email value
     *
     * @param email its a uuser's email value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REG_EXP);
    }

    /**
     * Validate user's birth date
     *
     * @param birthday its a birth date in format YYYY-MM-DD
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidBirthday(String birthday) {
        return birthday != null && birthday.matches(BIRTHDAY_REG_EXP);
    }
}
