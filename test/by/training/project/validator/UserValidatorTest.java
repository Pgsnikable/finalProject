package by.training.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorTest {

    @DataProvider
    public static Object[][] loginDataProvider() {
        return new Object[][]{
                {"pgsnik", true},
                {"ab", false},
                {"abcd123", true}
        };
    }

    @DataProvider
    public static Object[][] passDataProvider() {
        return new Object[][]{
                {"A123", true},
                {"a13", false}
        };
    }

    @Test(dataProvider = "loginDataProvider")
    public void testIsValidLogin(String login, boolean expected) {
        boolean actual = UserValidator.isValidLogin(login);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "passDataProvider")
    public void testIsValidPassword(String password, boolean expected) {
        boolean actual = UserValidator.isValidPassword(password);
        assertEquals(actual, expected);
    }
}