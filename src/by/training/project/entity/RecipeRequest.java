package by.training.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeRequest implements Entity {
    private Long recipeRequestId;
    private Long userId;
    private Long drugDosageId;
    private LocalDate expirationDate;
    private OrderOrRecipeStatus status;
}
