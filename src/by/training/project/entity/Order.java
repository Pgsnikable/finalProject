package by.training.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order implements Entity {
    private Long orderId;
    private Long userId;
    private Long drugDosageId;
    private LocalDateTime orderTime;
    private Integer quantity;
    private BigDecimal orderCost;
    private OrderOrRecipeStatus status;
}
