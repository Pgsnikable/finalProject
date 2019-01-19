package by.training.project.dbconnection;

import by.training.project.exception.ConnectionPoolException;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    /**
     * @return {@code ConnectionManager} instance
     */
    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    /**
     * @return {@code ProxyConnection} is a connection (session) with a database.
     * @throws ConnectionPoolException if a database access error occurs
     *                                 or this method is called on a closed connection
     */
    public ProxyConnection getProxyConnection() throws ConnectionPoolException {
        try {
            return new ProxyConnection(DriverManager.getConnection(PropertiesManager.getInstance().get(URL_KEY),
                    PropertiesManager.getInstance().get(USER_KEY),
                    PropertiesManager.getInstance().get(PASSWORD_KEY)));
        } catch (SQLException e) {
            throw new ConnectionPoolException("Database access error", e);
        }
    }


}
