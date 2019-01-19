package by.training.project.dao;

import by.training.project.entity.Order;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    /**
     * Save in database new user's {@code Order}
     *
     * @param order is the {@code Order} which need to save
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean saveOrder(Order order) throws DaoException;

    /**
     * Update in database user's order by new value
     *
     * @param oldOrder is a {@code Order} which need to update
     * @param newOrder is a {@cods Order} by which need to update
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean changeOrder(Order oldOrder, Order newOrder) throws DaoException;

    /**
     * Find all user's orders
     *
     * @param userId is id of a user which orders need to find
     * @return {@code List} of user's orders or empty {@code List}
     * if user dont have any
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<Order> findAllByUserId(Long userId) throws DaoException;

    /**
     * Subtract user's balance by new order cost
     *
     * @param orderCost is a {@code BigDecimal} cost of new user's order
     * @param buyerId   is id of the user which make an order
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean subtractBalance(BigDecimal orderCost, Long buyerId) throws DaoException;

    /**
     * Return back cost of declined order
     *
     * @param orderCost is a {@code BigDecimal} cost of declined order
     * @param buyerId   is id of the user which decline order
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean returnBalance(BigDecimal orderCost, Long buyerId) throws DaoException;

    /**
     * Update status of the order by user decision
     *
     * @param orderId   is id of th order which need to update
     * @param newStatus its a new value of {@code OrderOrRecipeStatus}
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean updateOrderStatus(Long orderId, OrderOrRecipeStatus newStatus) throws DaoException;


}
