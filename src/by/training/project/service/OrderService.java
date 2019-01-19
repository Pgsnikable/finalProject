package by.training.project.service;

import by.training.project.dao.impl.OrderDaoImpl;
import by.training.project.dao.impl.UserDaoImpl;
import by.training.project.entity.Order;
import by.training.project.entity.OrderOrRecipeStatus;
import by.training.project.entity.UserPaymentData;
import by.training.project.exception.DaoException;
import by.training.project.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.training.project.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;

@SuppressWarnings({"unused", "Duplicates"})
public class OrderService {
    private static OrderService instance = new OrderService();

    /**
     * return the instance of the {@code OrderService}
     *
     * @return instance of the {@code OrderService}
     */
    public static OrderService getInstance() {
        return instance;
    }

    private OrderService() {
    }

    /**
     * Save into base user's order
     *
     * @param order its a {@code Order} object which
     *              need to save
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean saveOrder(Order order) throws ServiceException {
        boolean result;
        try {
            result = OrderDaoImpl.getInstance().saveOrder(order);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Subtract user's balance by order's cost
     *
     * @param orderCost its a {@code BigDecimal} value of the order cost
     * @param buyerId   its a buyer id
     * @return updated by balance {@code UserPaymentData}
     * @throws ServiceException if Dao layer can't do own method
     */
    public UserPaymentData payOrder(BigDecimal orderCost, Long buyerId) throws ServiceException {
        UserPaymentData newPaymentData = null;
        try {
            if (OrderDaoImpl.getInstance().subtractBalance(orderCost, buyerId)) {
                Optional<UserPaymentData> updatedPaymentData = UserDaoImpl.getInstance().findPaymentDataByUserId(buyerId);
                if (updatedPaymentData.isPresent()) {
                    newPaymentData = updatedPaymentData.get();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return newPaymentData;
    }

    /**
     * Return order's cost if order was declined
     *
     * @param orderCost its a cost of the declined order
     * @param buyerId   its a buyer id
     * @return updated by balance {@code UserPaymentData}
     * @throws ServiceException if Dao layer can't do own method
     */
    public UserPaymentData returnOrderCost(BigDecimal orderCost, Long buyerId) throws ServiceException {
        UserPaymentData newPaymentData = null;
        try {
            if (OrderDaoImpl.getInstance().returnBalance(orderCost, buyerId)) {
                Optional<UserPaymentData> updatedPaymentData = UserDaoImpl.getInstance().findPaymentDataByUserId(buyerId);
                if (updatedPaymentData.isPresent()) {
                    newPaymentData = updatedPaymentData.get();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return newPaymentData;
    }

    /**
     * Return {@code List} of the user's order
     *
     * @param userId its id of the user which orders need to find
     * @return {@code List} of the user's order
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Order> getOrderListByUserId(Long userId) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = OrderDaoImpl.getInstance().findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return orderList;
    }

    /**
     * Delete from database user's order
     *
     * @param orderId its order id which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteOrder(Long orderId) throws ServiceException {
        boolean result;
        try {
            result = OrderDaoImpl.getInstance().delete(orderId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Update user order after user changes
     *
     * @param oldOrder its order which need to update
     * @param newOrder its order by which update
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean updateOrderByUserChanges(Order oldOrder, Order newOrder) throws ServiceException {
        boolean result = false;
        try {
            if (OrderDaoImpl.getInstance().changeOrder(oldOrder, newOrder)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Find user's order by order's id
     *
     * @param orderId its id of the order which need to find
     * @return {@code Optional} of {@code Order} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<Order> findOrderById(Long orderId) throws ServiceException {
        Optional<Order> maybeOrder;
        try {
            maybeOrder = OrderDaoImpl.getInstance().findById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeOrder;
    }

    /**
     * Find all orders of the all users
     *
     * @return {@code List} of the all users order
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = OrderDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return orders;
    }

    /**
     * Update order status
     *
     * @param orderId   its id of the order which status need to update
     * @param newStatus its a new order's status
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean updateOrderStatus(Long orderId, OrderOrRecipeStatus newStatus) throws ServiceException {
        boolean result = false;
        try {
            if (OrderDaoImpl.getInstance().updateOrderStatus(orderId, newStatus)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Create {@code Order} object from data
     *
     * @param userId    its a buyer id
     * @param dosageId  its a dosage which buyer need
     * @param orderTime its a order's time
     * @param quantity  its a order's quantity
     * @param orderCost its a order's cost
     * @param status    its a new order's status
     * @return {@code Order} object created from data
     */
    public Order makeOrder(Long userId, Long dosageId, LocalDateTime orderTime, Integer quantity, BigDecimal orderCost, OrderOrRecipeStatus status) {
        return buildOrder(userId, dosageId, orderTime, quantity, orderCost, status);
    }

    private Order buildOrder(Long userId, Long dosageId, LocalDateTime orderTime, Integer quantity, BigDecimal orderCost, OrderOrRecipeStatus status) {
        return Order.builder()
                .drugDosageId(dosageId)
                .userId(userId)
                .orderTime(orderTime)
                .quantity(quantity)
                .orderCost(orderCost)
                .status(status)
                .build();
    }
}
