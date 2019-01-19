package by.training.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorTest {

    @DataProvider
    public static Object[][] fistNameDataProvider() {
        return new Object[][]{
                {"Иван", true},
                {"Илья", true},
                {"John", true},
                {"aNDY", false},
                {"1ad", false}
        };
    }

    @DataProvider
    public static Object[][] lastNameDataProvider() {
        return new Object[][]{
                {"Петров", true},
                {"Иванов", true},
                {"Johnson", true},
                {"aNDY", false},
                {"1ad", false}
        };
    }

    @DataProvider
    public static Object[][] emailDataProvider() {
        return new Object[][]{
                {"pgsnik1234@mail.ru", true},
                {"ivanov_123@gov.mvd.by", true},
                {"blinov@gmail.com", true},
                {"aNDY@asad@asd.com", false},
                {"asd@gov.ua.by.ru", false}
        };
    }

    @DataProvider
    public static Object[][] birthdayDataProvider() {
        return new Object[][]{
                {"1991-07-12", true},
                {"1991-01-30", true},
                {"1991-31-32", false},
                {"1995-02-31", false}};
    }

    @Test(dataProvider = "fistNameDataProvider")
    public void testIsValidFirstName(String firstName, boolean expected) {
        boolean actual = UserDataValidator.isValidFirstName(firstName);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "lastNameDataProvider")
    public void testIsValidLastName(String lastName, boolean expected) {
        boolean actual = UserDataValidator.isValidLastName(lastName);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "emailDataProvider")
    public void testIsValidEmail(String email, boolean expected) {
        boolean actual = UserDataValidator.isValidEmail(email);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "birthdayDataProvider")
    public void testIsValidBirthday(String birthday, boolean expected) {
        boolean actual = UserDataValidator.isValidBirthday(birthday);
        assertEquals(actual, expected);
    }
}