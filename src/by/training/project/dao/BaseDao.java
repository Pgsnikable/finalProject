package by.training.project.dao;

import by.training.project.exception.DaoException;
import by.training.project.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Entity> {
    /**
     * Find any {@code Entity} by it id and return {@code Optional}
     * value of it or empty {@code optional}
     *
     * @param id is a {@code Entity} id by which it will be found
     * @return {@code Optional} of {@code Entity} if present or
     * empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<? extends Entity> findById(Long id) throws DaoException;

    /**
     * Delete any {@code Entity} by it id and return {@code true}
     * if {@code Entity} was successfully deleted or {@code false}
     * if it failed
     *
     * @param entityId which need to delete
     * @return {@code boolean} value - {@code true} if success
     * or {@code false} if fail
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean delete(Long entityId) throws DaoException;

    /**
     * Find all entities in database
     *
     * @return the {@code List} of some entities
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    List<? extends Entity> findAll() throws DaoException;

    /**
     * Find {@code Entity} by it name if present
     *
     * @param entityName by which will be found
     * @return {@code Optional} value of {@code Entity} if present
     * or empty {@code Optional}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Optional<? extends Entity> findByEntityName(String entityName) throws DaoException;

    /**
     * Update {@code Entity} value in database by new value of it
     *
     * @param entity is a new value of {@code Entity}
     * @return new value of {@code Entity}
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    Entity update(T entity) throws DaoException;

}
