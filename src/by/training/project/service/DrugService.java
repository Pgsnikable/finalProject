package by.training.project.service;

import by.training.project.dao.impl.DrugDaoImpl;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugCategory;
import by.training.project.entity.DrugDosage;
import by.training.project.exception.DaoException;
import by.training.project.exception.ServiceException;

import java.math.BigDecimal;
import java.util.*;

import static by.training.project.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;

@SuppressWarnings("Duplicates")
public class DrugService {
    private static DrugService instance = new DrugService();

    /**
     * Return a instance of {@code DrugService}
     *
     * @return {@code DrugService} instance
     */
    public static DrugService getInstance() {
        return instance;
    }

    private DrugService() {
    }

    /**
     * Find all category of the drugs
     *
     * @return {@code List} of the drug categories
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<DrugCategory> findAllDrugCategory() throws ServiceException {
        List<DrugCategory> category;
        try {
            category = DrugDaoImpl.getInstance().findAllDrugCategory();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return category;
    }

    /**
     * Find all drugs and their dosages of current category
     *
     * @param drugCategoryId its id of drug category whose drugs
     *                       need to find
     * @return {@code List} parametrized {@code Map.Entry} which contains pair
     * of the drug and his dosages list
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Map.Entry<Drug, List<DrugDosage>>> findAllDrugsByCategoryId(Long drugCategoryId) throws ServiceException {
        List<Map.Entry<Drug, List<DrugDosage>>> drugList;
        try {
            List<Drug> drugsByCategoryId = DrugDaoImpl.getInstance().findAllDrugsByCategoryId(drugCategoryId);
            Map<Drug, List<DrugDosage>> drugMap = new HashMap<>();
            for (Drug drug : drugsByCategoryId) {
                List<DrugDosage> dosagesByDrugId = DrugDaoImpl.getInstance().findAllDosagesByDrugId(drug.getDrugId());
                drugMap.put(drug, dosagesByDrugId);
            }
            drugList = new ArrayList<>(drugMap.entrySet());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return drugList;
    }

    /**
     * Find all drugs and their dosages which need recipe
     *
     * @return {@code List} parametrized {@code Map.Entry} which contains pair
     * of the drug and his dosages list
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Map.Entry<Drug, List<DrugDosage>>> findAllRecipeNedeedDrugs() throws ServiceException {
        List<Map.Entry<Drug, List<DrugDosage>>> drugList = new ArrayList<>();
        try {
            List<Drug> recipeNedeedDrugs = DrugDaoImpl.getInstance().findAllRecipeNedeedDrugs();
            for (Drug recipeNedeedDrug : recipeNedeedDrugs) {
                List<DrugDosage> allDosagesByDrugId = DrugDaoImpl.getInstance().findAllDosagesByDrugId(recipeNedeedDrug.getDrugId());
                drugList.add(new AbstractMap.SimpleEntry<>(recipeNedeedDrug, allDosagesByDrugId));
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return drugList;
    }

    /**
     * Find drug dosage by id
     *
     * @param drugDosageId its a id of {@code DrugDosage}
     *                     which need to find
     * @return {@code Optional} of {@code DrugDosage} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<DrugDosage> findDrugDosageById(Long drugDosageId) throws ServiceException {
        Optional<DrugDosage> maybeDosage;
        try {
            maybeDosage = DrugDaoImpl.getInstance().findDrugDosageById(drugDosageId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeDosage;
    }

    /**
     * Find drug by his id
     *
     * @param drugId its id of the drug which need
     *               to find
     * @return {@code Optional} of {@code Drug} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<Drug> findDrugById(Long drugId) throws ServiceException {
        Optional<Drug> maybeDrug;
        try {
            maybeDrug = DrugDaoImpl.getInstance().findById(drugId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeDrug;
    }

    /**
     * Find all dosages of the current drug
     *
     * @param drugId its id of the drug whose dosages
     *               need to find
     * @return {@code List} of the dosages
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<DrugDosage> findAllDrugDosageByDrugId(Long drugId) throws ServiceException {
        List<DrugDosage> drugDosages;
        try {
            drugDosages = DrugDaoImpl.getInstance().findAllDosagesByDrugId(drugId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return drugDosages;
    }

    /**
     * Subtract drug storage quantity by order quantity
     *
     * @param subtrahedQuantity its a order quantity
     * @param drugId            its id of the drug which storage quantity
     *                          need to subtract
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean subtractDrugStorageQuantity(Integer subtrahedQuantity, Long drugId) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().subtractDrugQuantity(subtrahedQuantity, drugId)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Return back drug quantity into storage by declined order
     *
     * @param returnedQuantity its a quantity which need to return back
     * @param drugId           its id of the drug whose quantity returned
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean returnDrugToStorageQuantity(Integer returnedQuantity, Long drugId) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().returnDrugQuantity(returnedQuantity, drugId)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Add new drug category
     *
     * @param categoryName its a name of the new {@code DrugCategory}
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean addCategory(String categoryName) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().addCategory(categoryName)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Add new drug
     *
     * @param drug its a new drug which need to add
     * @return {@code Long} id of the new drug after saving
     * @throws ServiceException if Dao layer can't do own method
     */
    public Long addDrug(Drug drug) throws ServiceException {
        Long drugId;
        try {
            drugId = DrugDaoImpl.getInstance().saveDrug(drug);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return drugId;
    }

    /**
     * Add new drug dosage
     *
     * @param drugId its id the drug in which add new dosage
     * @param dosage its a {@code String} name of the new dosage
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean addDrugDosage(Long drugId, String dosage) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().saveDrugDosage(drugId, dosage)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Find all drugs
     *
     * @return {@code List} of the all drugs
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Drug> findAllDrugs() throws ServiceException {
        List<Drug> drugList;
        try {
            drugList = DrugDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return drugList;
    }

    /**
     * Delete from base drug by his id
     *
     * @param drugId its id of the drug which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteDrug(Long drugId) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().delete(drugId)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Delete from base drug category by id
     *
     * @param categoryId its id of the {@code DrugCategory} which
     *                   need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteCategory(Long categoryId) throws ServiceException {
        boolean result = false;
        try {
            if (DrugDaoImpl.getInstance().deleteDrugCategory(categoryId)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * make a new {@code Drug} object from data
     *
     * @param name              its a drug name
     * @param composition       its a drug composition
     * @param indications       its a drug indications
     * @param modeOfApplication its a drug mode of application
     * @param contraindications its a drug contraindications
     * @param recipeNeeded      its a {@code boolean} value need the recipe
     * @param categoryId        its id of the drug category
     * @param quantity          its a storage quantity
     * @param price             its a price per unit
     * @return {@code Drug} object build from data
     */
    public Drug makeDrug(String name, String composition,
                         String indications, String modeOfApplication, String contraindications,
                         Boolean recipeNeeded, Long categoryId, Integer quantity, BigDecimal price) {
        return buildDrug(name, composition, indications, modeOfApplication, contraindications, recipeNeeded, categoryId, quantity, price);
    }

    private Drug buildDrug(String name, String composition, String indications, String modeOfApplication,
                           String contraindications, Boolean recipeNeeded, Long categoryId, Integer quantity, BigDecimal price) {
        return Drug.builder()
                .name(name)
                .composition(composition)
                .indications(indications)
                .modeOfApplication(modeOfApplication)
                .contraindications(contraindications)
                .recipeNedeed(recipeNeeded)
                .drugCategoryId(categoryId)
                .storageQuantity(quantity)
                .price(price)
                .build();
    }

}
