package by.training.project.dao;

import by.training.project.entity.UserWithoutPassword;
import by.training.project.exception.DaoException;
import by.training.project.entity.*;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    /**
     * Save new admin or doctor user in database
     *
     * @param entity its a new admin or doctor entity
     * @return {@code UserWithoutPassword} for saving into {@code HttpSession}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    UserWithoutPassword saveUser(User entity) throws DaoException;

    /**
     * Save new user in database with {@code UserData}
     * and {@code UserPaymentData}
     *
     * @param entity      is a new {@code User} entity
     * @param userData    is a new {@code UserData}
     * @param paymentData is a new {@code UserPaymentData}
     * @return {@code UserWithoutPassword} for saving into {@code HttpSession}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    UserWithoutPassword saveUser(User entity, UserData userData, UserPaymentData paymentData) throws DaoException;

    /**
     * Find {@code UserPaymentData} in database by user's id
     *
     * @param userId is id of the user whose {@code UserPaymentData}
     *               need to find
     * @return {@code Optional} of {@code UserPaymentData} if value present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<UserPaymentData> findPaymentDataByUserId(Long userId) throws DaoException;

    /**
     * Find in database {@code UserData} by email value
     *
     * @param email its {@code String} value of the email
     * @return {@code Optional} of {@code UserData} if value present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<UserData> findDataByEmail(String email) throws DaoException;

    /**
     * Find in database {@code UserData} by user's id
     *
     * @param userId is id of the user whose {@code UserData}
     *               need to find
     * @return {@code Optional} of {@code UserData} if value present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<UserData> findDataByUserId(Long userId) throws DaoException;

    /**
     * Check in database {@code User} by his login and password value
     *
     * @param login    its a {@code String} login value
     * @param password its a {@code String} encoding password value
     * @return {@code Optional} of {@code UserWithoutPassword} if value present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<UserWithoutPassword> findUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Add balance on the user's bill in application
     *
     * @param addBalance the {@code BigDecimal} value which need
     *                   to add at current balance value
     * @param userId     its id of the user whose balance add
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean addBalance(BigDecimal addBalance, Long userId) throws DaoException;

    /**
     * Update in database user's data
     *
     * @param user        its a user whose data will be updated
     * @param userData    its a new {@code UserData}
     * @param paymentData its a new {@code userPaymentData}
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean updateUserData(UserWithoutPassword user, UserData userData, UserPaymentData paymentData) throws DaoException;

    /**
     * Delete any user from database
     *
     * @param userId is id of the user which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean deleteUser(Long userId) throws DaoException;
}
