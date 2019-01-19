package by.training.project.service;

import by.training.project.dao.impl.RecipeDaoImpl;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.entity.Recipe;
import by.training.project.entity.RecipeRequest;
import by.training.project.exception.DaoException;
import by.training.project.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.training.project.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;

public class RecipeService {
    private static RecipeService instance = new RecipeService();

    /**
     * Return instance of the {@code RecipeService}
     *
     * @return instance of the {@code RecipeService}
     */
    public static RecipeService getInstance() {
        return instance;
    }

    private RecipeService() {
    }

    /**
     * Create request from data
     *
     * @param userId         its a user who make request id
     * @param dosageId       its a dosage which user need
     * @param expirationDate its a recipe expiration date
     * @param status         its new request status
     * @return {@code RecipeRequest} object created from data
     */
    public RecipeRequest makeRequest(Long userId, Long dosageId, LocalDate expirationDate, OrderOrRecipeStatus status) {
        return buildRecipeRequest(userId, dosageId, expirationDate, status);
    }

    /**
     * Find current recipe which contains user's and dosage's id
     *
     * @param userId   its a recipe owner id
     * @param dosageId its a current drug dosage id
     * @return {@code Optional} of {@code Drug} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<Recipe> findRecipeByUserIdAndDosageId(Long userId, Long dosageId) throws ServiceException {
        Optional<Recipe> maybeRecipe;
        try {
            maybeRecipe = RecipeDaoImpl.getInstance().findRecipeByUserIdAndDosageId(userId, dosageId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeRecipe;
    }

    /**
     * Find all user's recipes
     *
     * @param userId its a id of the user which recipes need to found
     * @return {@code List} of the recipes
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Recipe> findAllRecipesByUserId(Long userId) throws ServiceException {
        List<Recipe> recipes;
        try {
            recipes = RecipeDaoImpl.getInstance().findAllRecipeByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return recipes;
    }

    /**
     * Find all user's recipe requests
     *
     * @param userId its a id of the user which recipe request need to found
     * @return {@code List} of the recipe requests
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<RecipeRequest> findAllRecipeRequestByUserId(Long userId) throws ServiceException {
        List<RecipeRequest> recipes;
        try {
            recipes = RecipeDaoImpl.getInstance().findAllRecipeRequestByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return recipes;
    }

    /**
     * Create a new {@code RecipeRequest} and save it in database
     *
     * @param recipeRequest its a {@code RecipeRequest} object
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean sendRecipeRequest(RecipeRequest recipeRequest) throws ServiceException {
        boolean result;
        try {
            result = RecipeDaoImpl.getInstance().saveRecipeRequest(recipeRequest);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Delete recipe request by his id
     *
     * @param recipeRequestId its id of the {@code RecipeRequest} which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteRecipeRequest(Long recipeRequestId) throws ServiceException {
        boolean result;
        try {
            result = RecipeDaoImpl.getInstance().deleteRecipeRequestById(recipeRequestId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Find all user's recipe requests by his id
     *
     * @return {@code List} of the recipe requests
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<RecipeRequest> findAllRecipeRequest() throws ServiceException {
        List<RecipeRequest> requestList;
        try {
            requestList = RecipeDaoImpl.getInstance().findAllRecipeRequest();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return requestList;
    }

    /**
     * Update recipe request status as DECLINED
     *
     * @param recipeRequestId its a recipe request which need to update
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean declineRecipeRequest(Long recipeRequestId) throws ServiceException {
        boolean result;
        try {
            result = RecipeDaoImpl.getInstance().declineRecipeRequest(recipeRequestId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Update recipe request status as ACCEPTED and create and save new recipe
     * by this request
     *
     * @param recipeRequest its a {@code RecipeRequest} object which accepted
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean acceptRecipeRequest(RecipeRequest recipeRequest) throws ServiceException {
        boolean result;
        try {
            result = RecipeDaoImpl.getInstance().acceptRecipeRequest(recipeRequest);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Find recipe request by his id
     *
     * @param requestId its id of the recipe request which need to find
     * @return {@code Optional} of {@code RecipeRequest} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<RecipeRequest> findRecipeRequestById(Long requestId) throws ServiceException {
        Optional<RecipeRequest> maybeRequest;
        try {
            maybeRequest = RecipeDaoImpl.getInstance().findRecipeRequestById(requestId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeRequest;
    }

    private RecipeRequest buildRecipeRequest(Long userId, Long dosageId, LocalDate expirationDate, OrderOrRecipeStatus status) {
        return RecipeRequest.builder()
                .userId(userId)
                .drugDosageId(dosageId)
                .expirationDate(expirationDate)
                .status(status)
                .build();
    }

}
