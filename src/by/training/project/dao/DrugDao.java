package by.training.project.dao;

import by.training.project.exception.DaoException;
import by.training.project.entity.*;

import java.util.List;

public interface DrugDao extends BaseDao<Drug> {
    /**
     * Find all {@code DrugCategory} in database
     *
     * @return the {@code List} of {@code DrugCategory}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<DrugCategory> findAllDrugCategory() throws DaoException;

    /**
     * Find all drugs in current {@code DrugCategory}
     *
     * @param drugCategoryId is a id of drug category in which
     *                       need to find all drugs
     * @return {@code List} of all drug in current drug category
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<Drug> findAllDrugsByCategoryId(Long drugCategoryId) throws DaoException;

    /**
     * Find all dosages of a current drug
     *
     * @param drugId is a id of drug all dosages that need to be found
     * @return {@code List} of {@code DrugDosage} of the drug
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<DrugDosage> findAllDosagesByDrugId(Long drugId) throws DaoException;

    /**
     * Subtract drug storage quantity by user order quantity
     *
     * @param subtrahendQuantity is a quantity of {@code Order}
     * @param drugId             is a id of the drug which quantity will be subtrahend
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean subtractDrugQuantity(Integer subtrahendQuantity, Long drugId) throws DaoException;

    /**
     * Return in the drug storage quantity of the current drug if order was declined or etc.
     *
     * @param returnedQuantity is {@code Integer} quantity which need to return back
     * @param drugId           is id of th drug whose quantity need toreturn
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean returnDrugQuantity(Integer returnedQuantity, Long drugId) throws DaoException;

    /**
     * Find a list of all drugs in database which need recipe to be bought
     *
     * @return {@code List} of drugs which need recipe to be bought
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<Drug> findAllRecipeNedeedDrugs() throws DaoException;

    /**
     * Add in database new {@code DrugCategory}
     *
     * @param categoryName is a {@code String} name of a new category
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean addCategory(String categoryName) throws DaoException;

    /**
     * Save new drug in database
     *
     * @param drug is a {@code Drug} which need to add in database
     * @return id number of new drug
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Long saveDrug(Drug drug) throws DaoException;

    /**
     * Save new dosage of drug in database
     *
     * @param drugId is id of the drug in which need add new dosage
     * @param dosage the name of the new dosage
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean saveDrugDosage(Long drugId, String dosage) throws DaoException;

    /**
     * Delete drug category
     *
     * @param categoryId is id of the {@code DrugCategory} which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean deleteDrugCategory(Long categoryId) throws DaoException;
}
