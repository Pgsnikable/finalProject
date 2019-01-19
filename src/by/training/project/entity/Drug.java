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
public class Drug implements Entity {
    private Long drugId;
    private String name;
    private String composition;
    private String indications;
    private String modeOfApplication;
    private String contraindications;
    private Boolean recipeNedeed;
    private Long drugCategoryId;
    private Integer storageQuantity;
    private BigDecimal price;
}
