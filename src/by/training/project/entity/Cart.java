package by.training.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart implements Entity {
    DrugDosage drugDosage;
    String drugName;
    Boolean recipe;
    Integer quantity;
    Long userId;
    BigDecimal totalPrice;
}
