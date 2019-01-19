package by.training.project.dao;

import by.training.project.entity.Recipe;
import by.training.project.entity.RecipeRequest;
import by.training.project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface RecipeDao extends BaseDao<Recipe> {
    /**
     * Find all user's recipes
     *
     * @param userId is id of current user which recipe will be found
     * @return {@code List} of the user's {@code Recipe}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<Recipe> findAllRecipeByUserId(Long userId) throws DaoException;

    /**
     * Check recipe in database by user's id and current
     * {@code DrugDosage} id
     *
     * @param userId   is id of the user which recipe need to check
     * @param dosageId its id of the {@code DrugDosage} which need
     *                 to check in recipe
     * @return {@code Optional} of {@code Recipe} value if present or
     * empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<Recipe> findRecipeByUserIdAndDosageId(Long userId, Long dosageId) throws DaoException;

    /**
     * Save in database user's request for new recipe
     * of a current drug and dosage
     *
     * @param recipeRequest its a new user's {@code RecipeRequest}
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean saveRecipeRequest(RecipeRequest recipeRequest) throws DaoException;

    /**
     * Find all users {@code RecipeRequest} in database
     *
     * @param userId is id of the user which {@code RecipeRequest}
     *               need to found
     * @return {@code List} of user's {@code RecipeRequest}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<RecipeRequest> findAllRecipeRequestByUserId(Long userId) throws DaoException;

    /**
     * Delete from database {@code RecipeRequest} of the current user
     *
     * @param recipeRequestId is id of the current {@code RecipeRequest}
     *                        which need to delete from database
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean deleteRecipeRequestById(Long recipeRequestId) throws DaoException;

    /**
     * Find all {@code RecipeRequest} which present in database
     *
     * @return {@code List} of {@code RecipeRequest}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<RecipeRequest> findAllRecipeRequest() throws DaoException;

    /**
     * Change {@code RecipeRequest} status at "DECLINED"
     *
     * @param recipeRequestId its id of the {@code RecipeRequest}
     *                        which need to update
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean declineRecipeRequest(Long recipeRequestId) throws DaoException;

    /**
     * Change status of {@code RecipeRequest} on "ACCEPTED" an
     * make and save in database new {@code Recipe} for this user
     *
     * @param recipeRequest its a {@code recipeRequest} which accepted
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean acceptRecipeRequest(RecipeRequest recipeRequest) throws DaoException;

    /**
     * Find in database {@code RecipeRequest} by it id
     *
     * @param recipeRequestId is id of {@code RecipeRequest}
     *                        which need to found
     * @return {@code Optional} of {@code RecipeRequest} if value present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<RecipeRequest> findRecipeRequestById(Long recipeRequestId) throws DaoException;
}
