package by.training.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserPaymentData implements Entity {
    private Long paymentId;
    private Long userId;
    private Long cardNumber;
    private BigDecimal balance;
}
