package by.training.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserPaymentDataValidatorTest {

    @DataProvider
    public static Object[][] cardDataProvider() {
        return new Object[][]{
                {"1234567812345678", true},
                {"12345678123456789", false}
        };
    }

    @Test(dataProvider = "cardDataProvider")
    public void testIsValidCardNumber(String cardNumber, boolean expected) {
        boolean actual = UserPaymentDataValidator.isValidCardNumber(cardNumber);
        assertEquals(actual, expected);
    }
}