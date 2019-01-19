package by.training.project.dbconnection;

import by.training.project.exception.ConnectionPoolException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;
    private ProxyConnection connection = null;


    @BeforeMethod
    public void setUp() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Test
    public void testGetConnection() {
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            Assert.fail();
        }
        Assert.assertNotNull(connection);
    }
}