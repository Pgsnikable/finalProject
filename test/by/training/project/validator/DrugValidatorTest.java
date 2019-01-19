package by.training.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DrugValidatorTest {

    @DataProvider
    public static Object[][] categoryNameDataProvider() {
        return new Object[][]{
                {"Витамины", true},
                {"Антибиотики", true},
                {"Противогрибковые средства", true},
                {"sAsd12", false}
        };
    }

    @DataProvider
    public static Object[][] drugNameDataProvider() {
        return new Object[][]{
                {"Аскорбиновая кислота", true},
                {"dsfsdfsdf", false},
                {"1", false}
        };
    }

    @DataProvider
    public static Object[][] dosageNameDataProvider() {
        return new Object[][]{
                {"asdasd]123", false},
                {"Таблетки 25х10 №2", true},
                {"витамины", false}
        };
    }

    @Test(dataProvider = "categoryNameDataProvider")
    public void testIsValidCategoryName(String categoryName, boolean expected) {
        boolean actual = DrugValidator.isValidCategoryName(categoryName);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "drugNameDataProvider")
    public void testIsValidDrugName(String drugName, boolean expected) {
        boolean actual = DrugValidator.isValidDrugName(drugName);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "dosageNameDataProvider")
    public void testIsValidDrugDosageName(String dosageName, boolean expected) {
        boolean actual = DrugValidator.isValidDrugDosageName(dosageName);
        assertEquals(actual, expected);
    }
}