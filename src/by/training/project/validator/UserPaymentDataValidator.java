package by.training.project.validator;

import by.training.project.entity.UserPaymentData;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class UserPaymentDataValidator {
    private static final String CARD_NUMBER_REG_EXP = "^\\d{16}$";

    /**
     * Validate user's card number value
     *
     * @param cardNumber its a user's card number value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches(CARD_NUMBER_REG_EXP);
    }

    /**
     * Check is enough user's balance to make order
     *
     * @param orderCost   its a order's cost
     * @param paymentData its user's payment data value
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isEnoughBalance(BigDecimal orderCost, UserPaymentData paymentData) {
        return paymentData.getBalance().compareTo(orderCost) >= 0;
    }
}
