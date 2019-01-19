package by.training.project.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DrugValidator {
    private static final String CATEGORY_NAME_REG_EXP = "^[A-Z][a-z\\s]{1,63}$|^[А-Я][а-яёъ\\s]{1,63}$";
    private static final String DRUG_NAME_REG_EXP = "^[A-Z][a-z\\s]{1,127}$|^[А-Я][а-яёъ\\s]{1,127}$";
    private static final String DRUG_DOSAGE_NAME_REG_EXP = "^[A-Z][a-z\\s\\D\\d]{1,63}$|^[А-Я][а-яёъ\\s\\D\\d]{1,63}$";
    private static final String COMPOSITION_REG_EXP = "^[A-Z][a-z\\s\\D\\d]{1,255}$|^[А-Я][а-яёъ\\s\\D\\d]{1,255}$";
    private static final String INDICATIONS_REG_EXP = "^[A-Z][a-z\\s\\D\\d]{1,255}$|^[А-Я][а-яёъ\\s\\D\\d]{1,255}$$";
    private static final String MODE_OF_APPLICATION_REG_EXP = "^[A-Z][a-z\\s\\D\\d]{1,255}$|^[А-Я][а-яёъ\\s\\D\\d]{1,255}$";
    private static final String CONTRAINDICATIONS_REG_EXP = "^[A-Z][a-z\\s\\D\\d]{1,255}$|^[А-Я][а-яёъ\\s\\D\\d]{1,255}$";


    /**
     * Validate drug category name
     *
     * @param categoryName its a category name which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidCategoryName(String categoryName) {
        return categoryName != null && categoryName.matches(CATEGORY_NAME_REG_EXP);
    }

    /**
     * Validate drug name
     *
     * @param drugName its a drug name which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidDrugName(String drugName) {
        return drugName != null && drugName.matches(DRUG_NAME_REG_EXP);
    }

    /**
     * Validate drug dosage name
     *
     * @param drugDosageName its a drug dosage name which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidDrugDosageName(String drugDosageName) {
        return drugDosageName != null && drugDosageName.matches(DRUG_DOSAGE_NAME_REG_EXP);
    }

    /**
     * Validate drug composition
     *
     * @param composition its a drug composition which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidComposition(String composition) {
        return composition != null && composition.matches(COMPOSITION_REG_EXP);
    }

    /**
     * Validate drug indications
     *
     * @param indications its a drug indications which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidIndications(String indications) {
        return indications != null && indications.matches(INDICATIONS_REG_EXP);
    }

    /**
     * Validate drug mode of application
     *
     * @param modeOfApplication its a drug mode of application which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidModeOfApplication(String modeOfApplication) {
        return modeOfApplication != null && modeOfApplication.matches(MODE_OF_APPLICATION_REG_EXP);
    }

    /**
     * Validate drug contraindications
     *
     * @param contraindications its a drug contraindications which need to validate
     * @return {@code true} if success or {@code false} if failed
     */
    public boolean isValidContraindications(String contraindications) {
        return contraindications != null && contraindications.matches(CONTRAINDICATIONS_REG_EXP);
    }
}
