package by.training.project.dao.impl;

import by.training.project.dao.OrderDao;
import by.training.project.dbconnection.ConnectionPool;
import by.training.project.dbconnection.ProxyConnection;
import by.training.project.entity.Entity;
import by.training.project.entity.Order;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.exception.ConnectionPoolException;
import by.training.project.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.project.exception.ExceptionMessage.*;

@SuppressWarnings("Duplicates")
public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl ourInstance = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return ourInstance;
    }

    private OrderDaoImpl() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SAVE_ORDER = "INSERT INTO " +
            "pharmacy_storage.user_order (user_id, dosage_id, quantity, order_time, order_cost, order_status) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_ORDERS_BY_USER_ID =
            "SELECT id, user_id, dosage_id, quantity, order_time, order_cost, order_status " +
                    "FROM pharmacy_storage.user_order WHERE user_id = ?";

    private static final String SQL_SUBTRACT_BALANCE = "UPDATE pharmacy_storage.payment_data SET balance = balance - ? WHERE user_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM pharmacy_storage.user_order WHERE id = ?";
    private static final String SQL_RETURN_BALANCE = "UPDATE pharmacy_storage.payment_data SET balance = balance + ? WHERE user_id = ?";
    private static final String SQL_CHANGE_ORDER_BY_USER = "UPDATE pharmacy_storage.user_order " +
            "SET dosage_id = ?, quantity = ?, order_time = ?, order_cost = ? WHERE id = ?";
    private static final String SQL_GET_ORDER_BY_ID = "SELECT id, user_id, dosage_id, quantity, order_time, order_cost, order_status" +
            " FROM pharmacy_storage.user_order WHERE id = ?";

    private static final String SQL_SUBTRACT_DRUG_QUANTITY_BY_ORDER_CHANGE = "UPDATE pharmacy_storage.drug " +
            "SET storage_quantity = storage_quantity - ? WHERE id = (SELECT drug_id FROM pharmacy_storage.drug_dosage WHERE drug_dosage.id = ?)";
    private static final String SQL_RETURN_DRUG_QUANTITY_BY_ORDER_CHANGE =
            "UPDATE pharmacy_storage.drug SET storage_quantity = storage_quantity + ? WHERE id = " +
                    "(SELECT drug_id FROM pharmacy_storage.drug_dosage WHERE drug_dosage.id = ?)";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT id, user_id, dosage_id, quantity, order_time, order_cost, order_status" +
            " FROM pharmacy_storage.user_order";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE pharmacy_storage.user_order SET order_status = ? WHERE id = ?";


    @Override
    public boolean saveOrder(Order order) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getDrugDosageId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getOrderTime()));
            preparedStatement.setBigDecimal(5, order.getOrderCost());
            preparedStatement.setInt(6, order.getStatus().ordinal());
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
    public List<Order> findAllByUserId(Long userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public boolean subtractBalance(BigDecimal cost, Long buyerId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SUBTRACT_BALANCE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBigDecimal(1, cost);
            preparedStatement.setLong(2, buyerId);
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
    public boolean returnBalance(BigDecimal orderCost, Long buyerId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_RETURN_BALANCE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBigDecimal(1, orderCost);
            preparedStatement.setLong(2, buyerId);
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
    public boolean updateOrderStatus(Long orderId, OrderOrRecipeStatus newStatus) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, newStatus.ordinal());
            preparedStatement.setLong(2, orderId);
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
    public Optional<Order> findById(Long id) throws DaoException {
        Optional<Order> maybeOrder = Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                maybeOrder = Optional.of(buildOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return maybeOrder;
    }

    @Override
    public boolean delete(Long entityId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entityId);
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
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public Optional<? extends Entity> findByEntityName(String entityName) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Order update(Order entity) throws DaoException {
        return null;
    }

    @Override
    public boolean changeOrder(Order oldOrder, Order newOrder) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement returnBalanceStatement = null;
        PreparedStatement returnDrugStatement = null;
        PreparedStatement subtractBalanceStatement = null;
        PreparedStatement subtractDrugStatement = null;
        PreparedStatement changeOrderStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);


            returnBalanceStatement = connection.prepareStatement(SQL_RETURN_BALANCE);
            returnBalanceStatement.setBigDecimal(1, oldOrder.getOrderCost());
            returnBalanceStatement.setLong(2, oldOrder.getUserId());
            returnBalanceStatement.executeUpdate();

            returnDrugStatement = connection.prepareStatement(SQL_RETURN_DRUG_QUANTITY_BY_ORDER_CHANGE);
            returnDrugStatement.setInt(1, oldOrder.getQuantity());
            returnDrugStatement.setLong(2, oldOrder.getDrugDosageId());
            returnDrugStatement.executeUpdate();

            subtractBalanceStatement = connection.prepareStatement(SQL_SUBTRACT_BALANCE);
            subtractBalanceStatement.setBigDecimal(1, newOrder.getOrderCost());
            subtractBalanceStatement.setLong(2, newOrder.getUserId());
            subtractBalanceStatement.executeUpdate();

            subtractDrugStatement = connection.prepareStatement(SQL_SUBTRACT_DRUG_QUANTITY_BY_ORDER_CHANGE);
            subtractDrugStatement.setInt(1, newOrder.getQuantity());
            subtractDrugStatement.setLong(2, newOrder.getDrugDosageId());
            subtractDrugStatement.executeUpdate();

            changeOrderStatement = connection.prepareStatement(SQL_CHANGE_ORDER_BY_USER);
            changeOrderStatement.setLong(1, newOrder.getDrugDosageId());
            changeOrderStatement.setInt(2, newOrder.getQuantity());
            changeOrderStatement.setTimestamp(3, Timestamp.valueOf(newOrder.getOrderTime()));
            changeOrderStatement.setBigDecimal(4, newOrder.getOrderCost());
            changeOrderStatement.setLong(5, newOrder.getOrderId());
            changeOrderStatement.executeUpdate();

            connection.commit();
            result = true;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException(TRANSACTION_EXCEPTION_MESSAGE, e1);
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(returnBalanceStatement);
            closeStatement(returnDrugStatement);
            closeStatement(subtractBalanceStatement);
            closeStatement(subtractDrugStatement);
            closeStatement(changeOrderStatement);
            closeConnection(connection);
        }
        return result;
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

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .orderId(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .drugDosageId(resultSet.getLong("dosage_id"))
                .quantity(resultSet.getInt("quantity"))
                .orderTime(resultSet.getTimestamp("order_time").toLocalDateTime())
                .orderCost(resultSet.getBigDecimal("order_cost"))
                .status(OrderOrRecipeStatus.values()[resultSet.getInt("order_status")])
                .build();
    }
}
