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
public class Recipe implements Entity {
    private Long recipeId;
    private String recipeNumber;
    private Long userId;
    private Long drugDosageId;
    private LocalDate expirationDate;

}
