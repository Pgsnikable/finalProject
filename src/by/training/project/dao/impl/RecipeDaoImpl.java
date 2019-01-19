package by.training.project.dao.impl;

import by.training.project.dao.RecipeDao;
import by.training.project.dbconnection.ConnectionPool;
import by.training.project.dbconnection.ProxyConnection;
import by.training.project.entity.*;
import by.training.project.exception.ConnectionPoolException;
import by.training.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.project.exception.ExceptionMessage.*;

@SuppressWarnings("Duplicates")
public class RecipeDaoImpl implements RecipeDao {
    private static RecipeDaoImpl instance = new RecipeDaoImpl();

    public static RecipeDaoImpl getInstance() {
        return instance;
    }

    private RecipeDaoImpl() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_FIND_RECIPE_BY_USER_ID_AND_DOSAGE_ID =
            "SELECT id, user_id, dosage_id, expiration_date " +
                    "FROM pharmacy_storage.recipe WHERE user_id = ? AND dosage_id = ?";
    private static final String SQL_FIND_ALL_RECIPES_BY_USER_ID = "SELECT id, user_id, dosage_id, expiration_date" +
            " FROM pharmacy_storage.recipe WHERE user_id = ?";

    private static final String SQL_SAVE_RECIPE_REQUEST =
            "INSERT INTO pharmacy_storage.recipe_request (user_id, drug_dosage_id, expiration_date, request_status) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_RECIPE_REQUEST_BY_USER_ID =
            "SELECT id, user_id, drug_dosage_id, expiration_date, request_status " +
                    "FROM pharmacy_storage.recipe_request WHERE user_id = ?";
    private static final String SQL_FIND_ALL_RECIPE_REQUEST =
            "SELECT id, user_id, drug_dosage_id, expiration_date, request_status " +
                    "FROM pharmacy_storage.recipe_request";

    private static final String SQL_DELETE_RECIPE_REQUEST_BY_ID = "DELETE FROM pharmacy_storage.recipe_request WHERE id = ?";

    private static final String SQL_DECLINE_RECIPE_REQUEST_BY_ID = "UPDATE pharmacy_storage.recipe_request SET request_status = 1 WHERE id = ?";

    private static final String SQL_ACCEPT_RECIPE_REQUEST_BY_ID = "UPDATE pharmacy_storage.recipe_request SET request_status = 0 WHERE id = ?";
    private static final String SQL_SAVE_RECIPE_BY_REQUEST = "INSERT INTO pharmacy_storage.recipe (user_id, dosage_id, expiration_date) " +
            "VALUES (?, ?, ?)";
    private static final String SQL_FIND_RECIPE_REQUEST_BY_ID = "SELECT id, user_id, drug_dosage_id, expiration_date, request_status" +
            " FROM pharmacy_storage.recipe_request WHERE id = ?";


    @Override
    public Optional<Recipe> findRecipeByUserIdAndDosageId(Long userId, Long dosageId) throws DaoException {
        Optional<Recipe> maybeRecipe = Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_RECIPE_BY_USER_ID_AND_DOSAGE_ID);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, dosageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maybeRecipe = Optional.of(buildRecipe(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return maybeRecipe;
    }

    @Override
    public boolean saveRecipeRequest(RecipeRequest recipeRequest) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_RECIPE_REQUEST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, recipeRequest.getUserId());
            preparedStatement.setLong(2, recipeRequest.getDrugDosageId());
            preparedStatement.setDate(3, Date.valueOf(recipeRequest.getExpirationDate()));
            preparedStatement.setInt(4, recipeRequest.getStatus().ordinal());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<RecipeRequest> findAllRecipeRequestByUserId(Long userId) throws DaoException {
        List<RecipeRequest> requestList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_RECIPE_REQUEST_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                RecipeRequest recipeRequest = buildRecipeRequest(resultSet);
                requestList.add(recipeRequest);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return requestList;
    }

    @Override
    public boolean deleteRecipeRequestById(Long recipeRequestId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_RECIPE_REQUEST_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, recipeRequestId);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<RecipeRequest> findAllRecipeRequest() throws DaoException {
        List<RecipeRequest> requestList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_RECIPE_REQUEST);
            while (resultSet.next()){
                RecipeRequest recipeRequest = buildRecipeRequest(resultSet);
                requestList.add(recipeRequest);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return requestList;
    }

    @Override
    public boolean declineRecipeRequest(Long recipeRequestId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DECLINE_RECIPE_REQUEST_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, recipeRequestId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean acceptRecipeRequest(RecipeRequest recipeRequest) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement acceptRequestPreparedStatement = null;
        PreparedStatement saveRecipePreparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            acceptRequestPreparedStatement = connection.prepareStatement(SQL_ACCEPT_RECIPE_REQUEST_BY_ID);
            acceptRequestPreparedStatement.setLong(1, recipeRequest.getRecipeRequestId());
            acceptRequestPreparedStatement.executeUpdate();

            saveRecipePreparedStatement = connection.prepareStatement(SQL_SAVE_RECIPE_BY_REQUEST);
            saveRecipePreparedStatement.setLong(1, recipeRequest.getUserId());
            saveRecipePreparedStatement.setLong(2, recipeRequest.getDrugDosageId());
            saveRecipePreparedStatement.setDate(3, Date.valueOf(recipeRequest.getExpirationDate()));
            saveRecipePreparedStatement.executeUpdate();

            connection.commit();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException(TRANSACTION_EXCEPTION_MESSAGE, e1);
            }
        } finally {
            closeStatement(acceptRequestPreparedStatement);
            closeStatement(saveRecipePreparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public Optional<RecipeRequest> findRecipeRequestById(Long recipeRequestId) throws DaoException {
        Optional<RecipeRequest> maybeRequest =  Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_RECIPE_REQUEST_BY_ID);
            preparedStatement.setLong(1, recipeRequestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                maybeRequest = Optional.of(buildRecipeRequest(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return maybeRequest;
    }


    @Override
    public List<Recipe> findAllRecipeByUserId(Long userId) throws DaoException {
        List<Recipe> recipeList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_RECIPES_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = buildRecipe(resultSet);
                recipeList.add(recipe);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return recipeList;
    }

    @Override
    public Optional<? extends Entity> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long entityId) throws DaoException {
        return false;
    }

    @Override
    public List<? extends Entity> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<? extends Entity> findByEntityName(String entityName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Entity update(Recipe entity) throws DaoException {
        return null;
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't close statement", e);
        }
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't close proxy connection", e);
        }
    }

    private RecipeRequest buildRecipeRequest(ResultSet resultSet) throws SQLException {
        return RecipeRequest.builder()
                .recipeRequestId(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .drugDosageId(resultSet.getLong("drug_dosage_id"))
                .expirationDate(resultSet.getDate("expiration_date").toLocalDate())
                .status(OrderOrRecipeStatus.values()[resultSet.getInt("request_status")])
                .build();
    }

    private Recipe buildRecipe(ResultSet resultSet) throws SQLException {
        return Recipe.builder()
                .recipeId(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .drugDosageId(resultSet.getLong("dosage_id"))
                .expirationDate(resultSet.getDate("expiration_date").toLocalDate())
                .build();
    }



}
