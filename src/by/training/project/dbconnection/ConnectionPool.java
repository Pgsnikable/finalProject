package by.training.project.dbconnection;

import by.training.project.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DRIVER_KEY = "db.driver";
    private static final String SIZE_KEY = "db.poolsize";
    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> connections;

    private ConnectionPool() throws ConnectionPoolException {
        loadDriver();
        int poolSize = Integer.parseInt(PropertiesManager.getInstance().get(SIZE_KEY));
        this.connections = new LinkedBlockingDeque<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connections.offer(ConnectionManager.getInstance().getProxyConnection());
        }

    }

    /**
     * Create thread-safety <code>ConnectionPool</code> which consist of {@code ProxyConnection}
     *
     * @return thread-safety <code>ConnectionPool</code>
     * @throws RuntimeException if cant load JDBC driver or <code>ConnectionPool</code>
     *                          properties
     */
    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isPoolCreated.set(true);
                }
            } catch (ConnectionPoolException e) {
                throw new RuntimeException("Can't create connection pool!", e);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void loadDriver() throws ConnectionPoolException {
        try {
            PropertiesManager propertiesManager = PropertiesManager.getInstance();
            propertiesManager.loadProperties();
            Class.forName(propertiesManager.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't load JDBC driver", e);
        }
    }

    /**
     * Take {@code ProxyConnection} from pool
     *
     * @return <code>ProxyConnection</code> from {@code ConnectionPool}
     * @throws ConnectionPoolException if {@code ProxyConnection}
     *                                 interrupted while waiting
     */
    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Can't take connection from pool", e);
        }
        return connection;
    }

    /**
     * Return {@code ProxyConnection} back into pool and setAutoCommit(true)
     * before returning
     *
     * @param connection is a {@code ProxyConnection}
     */
    public void releaseConnection(ProxyConnection connection) {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Can't set autocommit", e);
        }
        connections.offer(connection);
    }

    /**
     * Close all {@code ProxyConnection} into {@code ConnectionPool}
     * and deregister JDBC driver
     */
    public void closePool() {
        try {
            while (!connections.isEmpty()) {
                connections.poll().closeConnection();
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
