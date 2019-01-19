package by.training.project.dao.impl;

import by.training.project.dao.DrugDao;
import by.training.project.dbconnection.ConnectionPool;
import by.training.project.dbconnection.ProxyConnection;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugCategory;
import by.training.project.entity.DrugDosage;
import by.training.project.entity.Entity;
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
public class DrugDaoImpl implements DrugDao {
    private static DrugDaoImpl instance = new DrugDaoImpl();

    public static DrugDaoImpl getInstance() {
        return instance;
    }

    private DrugDaoImpl() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_FIND_ALL_DRUG_CATEGORY = "SELECT id, category_name FROM pharmacy_storage.category";
    private static final String SQL_FIND_ALL_DRUGS_BY_CATEGORY_ID = "SELECT id, name, composition, indications, " +
            "mode_of_application, contraindications, recipe_needed, category_id, storage_quantity, price " +
            "FROM pharmacy_storage.drug WHERE drug.category_id = ?";
    private static final String SQL_FIND_ALL_DOSAGES_BY_DRUG_ID = "SELECT id, drug_id, dosage " +
            "FROM pharmacy_storage.drug_dosage WHERE drug_id = ?";
    private static final String SQL_FIND_ALL_DRUGS = "SELECT id, name, composition, indications, mode_of_application," +
            " contraindications, recipe_needed, category_id, storage_quantity, price " +
            "FROM pharmacy_storage.drug";
    private static final String SQL_FIND_DRUG_BY_ID = SQL_FIND_ALL_DRUGS + " WHERE id = ?";
    private static final String SQL_FIND_DOSAGE_BY_ID = "SELECT id, drug_id, dosage FROM pharmacy_storage.drug_dosage WHERE id = ?";
    private static final String SQL_SUBTRACT_DRUG_QUANTITY_BY_ORDER = "UPDATE pharmacy_storage.drug " +
            "SET storage_quantity = storage_quantity - ? WHERE id = ?";
    private static final String SQL_RETURN_DRUG_QUANTITY_BY_ORDER_DECLINE =
            "UPDATE pharmacy_storage.drug SET storage_quantity = storage_quantity + ? WHERE id = ?";
    private static final String SQL_FIND_ALL_RECIPE_NEEDED_DRUGS =
            "SELECT id, name, composition, indications, mode_of_application, contraindications, recipe_needed, category_id, storage_quantity, price " +
                    "FROM pharmacy_storage.drug WHERE recipe_needed = true";
    private static final String SQL_ADD_CATEGORY = "INSERT INTO pharmacy_storage.category (category_name) VALUES (?)";
    private static final String SQL_SAVE_DRUG =
            "INSERT INTO pharmacy_storage.drug (name, composition, indications, mode_of_application, contraindications, " +
                    "recipe_needed, category_id, storage_quantity, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SAVE_DRUG_DOSAGE = "INSERT INTO pharmacy_storage.drug_dosage (drug_id, dosage) VALUES (?, ?)";

    private static final String SQL_DELETE_DRUG_DOSAGE_FROM_RECIPE = "DELETE FROM pharmacy_storage.recipe " +
            "WHERE dosage_id = ?";
    private static final String SQL_DELETE_DRUG_DOSAGE_FROM_RECIPE_REQUEST = "DELETE FROM pharmacy_storage.recipe_request " +
            "WHERE drug_dosage_id = ?";
    private static final String SQL_DELETE_DRUG_DOSAGE_FROM_ORDER = "DELETE FROM pharmacy_storage.user_order " +
            "WHERE dosage_id = ?";
    private static final String SQL_DELETE_DRUG_FROM_DRUG_DOSAGE = "DELETE FROM pharmacy_storage.drug_dosage WHERE drug_id = ?";
    private static final String SQL_DELETE_DRUG = "DELETE FROM pharmacy_storage.drug WHERE id = ?";
    private static final String SQL_SELECT_ALL_DELETED_DOSAGES_ID = "SELECT id FROM pharmacy_storage.drug_dosage WHERE drug_id = ?";

    private static final String SQL_DELETE_CATEGORY_BY_ID = "DELETE FROM pharmacy_storage.category WHERE id = ?";


    @Override
    public List<DrugCategory> findAllDrugCategory() throws DaoException {
        List<DrugCategory> categoryList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_DRUG_CATEGORY);
            while (resultSet.next()) {
                DrugCategory category = buildCategory(resultSet);
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("Database error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return categoryList;
    }


    @Override
    public List<Drug> findAllDrugsByCategoryId(Long drugCategoryId) throws DaoException {
        List<Drug> drugList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_DRUGS_BY_CATEGORY_ID);
            preparedStatement.setLong(1, drugCategoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Drug drug = buildDrug(resultSet);
                drugList.add(drug);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return drugList;
    }

    @Override
    public List<DrugDosage> findAllDosagesByDrugId(Long drugId) throws DaoException {
        List<DrugDosage> dosageList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_DOSAGES_BY_DRUG_ID);
            preparedStatement.setLong(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DrugDosage drugDosage = buildDosage(resultSet);
                dosageList.add(drugDosage);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return dosageList;
    }

    @Override
    public boolean subtractDrugQuantity(Integer subtrahendQuantity, Long drugId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SUBTRACT_DRUG_QUANTITY_BY_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, subtrahendQuantity);
            preparedStatement.setLong(2, drugId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
    public boolean returnDrugQuantity(Integer returnedQuantity, Long drugId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_RETURN_DRUG_QUANTITY_BY_ORDER_DECLINE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, returnedQuantity);
            preparedStatement.setLong(2, drugId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
    public List<Drug> findAllRecipeNedeedDrugs() throws DaoException {
        List<Drug> drugList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_RECIPE_NEEDED_DRUGS);
            while (resultSet.next()) {
                Drug drug = buildDrug(resultSet);
                drugList.add(drug);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return drugList;
    }

    @Override
    public boolean addCategory(String categoryName) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
    public Long saveDrug(Drug drug) throws DaoException {
        Long drugId = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_DRUG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, drug.getName());
            preparedStatement.setString(2, drug.getComposition());
            preparedStatement.setString(3, drug.getIndications());
            preparedStatement.setString(4, drug.getModeOfApplication());
            preparedStatement.setString(5, drug.getContraindications());
            preparedStatement.setBoolean(6, drug.getRecipeNedeed());
            preparedStatement.setLong(7, drug.getDrugCategoryId());
            preparedStatement.setInt(8, drug.getStorageQuantity());
            preparedStatement.setBigDecimal(9, drug.getPrice());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                drugId = generatedKeys.getLong("id");
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return drugId;
    }

    @Override
    public boolean saveDrugDosage(Long drugId, String dosage) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_DRUG_DOSAGE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, drugId);
            preparedStatement.setString(2, dosage);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
    public boolean deleteDrugCategory(Long categoryId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_CATEGORY_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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


    public Optional<DrugDosage> findDrugDosageById(Long drugDosageId) throws DaoException {
        Optional<DrugDosage> maybeDosage = Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_DOSAGE_BY_ID);
            preparedStatement.setLong(1, drugDosageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maybeDosage = Optional.ofNullable(buildDosage(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return maybeDosage;
    }

    @Override
    public Optional<Drug> findById(Long id) throws DaoException {
        Optional<Drug> drug = Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(SQL_FIND_DRUG_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                drug = Optional.of(buildDrug(resultSet));
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return drug;
    }

    @Override
    public boolean delete(Long entityId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement recipePreparedStatement = null;
        PreparedStatement recipeRequestPreparedStatement = null;
        PreparedStatement orderPreparedStatement = null;
        PreparedStatement dosagePreparedStatement = null;
        PreparedStatement drugPreparedStatement = null;
        PreparedStatement allDosagesPreparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            allDosagesPreparedStatement = connection.prepareStatement(SQL_SELECT_ALL_DELETED_DOSAGES_ID);
            allDosagesPreparedStatement.setLong(1, entityId);
            ResultSet resultSet = allDosagesPreparedStatement.executeQuery();
            while (resultSet.next()) {
                recipePreparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_DOSAGE_FROM_RECIPE);
                recipePreparedStatement.setLong(1, resultSet.getLong("id"));
                recipePreparedStatement.executeUpdate();

                recipeRequestPreparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_DOSAGE_FROM_RECIPE_REQUEST);
                recipeRequestPreparedStatement.setLong(1, resultSet.getLong("id"));
                recipeRequestPreparedStatement.executeUpdate();

                orderPreparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_DOSAGE_FROM_ORDER);
                orderPreparedStatement.setLong(1, resultSet.getLong("id"));
                orderPreparedStatement.executeUpdate();
            }

            dosagePreparedStatement = connection.prepareStatement(SQL_DELETE_DRUG_FROM_DRUG_DOSAGE);
            dosagePreparedStatement.setLong(1, entityId);
            dosagePreparedStatement.executeUpdate();

            drugPreparedStatement = connection.prepareStatement(SQL_DELETE_DRUG);
            drugPreparedStatement.setLong(1, entityId);
            drugPreparedStatement.executeUpdate();

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
            closeStatement(allDosagesPreparedStatement);
            closeStatement(recipePreparedStatement);
            closeStatement(recipeRequestPreparedStatement);
            closeStatement(orderPreparedStatement);
            closeStatement(dosagePreparedStatement);
            closeStatement(drugPreparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Drug> findAll() throws DaoException {
        List<Drug> drugList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_DRUGS);
            while (resultSet.next()) {
                Drug drug = buildDrug(resultSet);
                drugList.add(drug);
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return drugList;
    }

    @Override
    public Optional<? extends Entity> findByEntityName(String entityName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Drug update(Drug entity) throws DaoException {
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

    private DrugCategory buildCategory(ResultSet resultSet) throws SQLException {
        return DrugCategory.builder()
                .categoryId(resultSet.getLong("id"))
                .name(resultSet.getString("category_name"))
                .build();
    }

    private DrugDosage buildDosage(ResultSet resultSet) throws SQLException {
        return DrugDosage.builder()
                .dosageId(resultSet.getLong("id"))
                .drugId(resultSet.getLong("drug_id"))
                .dosage(resultSet.getString("dosage"))
                .build();
    }

    private Drug buildDrug(ResultSet resultSet) throws SQLException {
        return Drug.builder()
                .drugId(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .composition(resultSet.getString("composition"))
                .indications(resultSet.getString("indications"))
                .modeOfApplication(resultSet.getString("mode_of_application"))
                .contraindications(resultSet.getString("contraindications"))
                .recipeNedeed(resultSet.getBoolean("recipe_needed"))
                .drugCategoryId(resultSet.getLong("category_id"))
                .storageQuantity(resultSet.getInt("storage_quantity"))
                .price(resultSet.getBigDecimal("price"))
                .build();
    }


}
