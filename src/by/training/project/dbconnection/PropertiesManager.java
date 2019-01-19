package by.training.project.dbconnection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesManager {
    private final static PropertiesManager instance = new PropertiesManager();

    public static PropertiesManager getInstance() {
        return instance;
    }

    private PropertiesManager() {

    }

    private static Logger logger = LogManager.getLogger();
    private Properties properties = new Properties();

    /**
     * load properties file which contains
     * URL, login, password, pool size and JDBC driver name
     * required for create connection with database
     */
    public void loadProperties() {
        try {
            InputStream resourceAsStream = PropertiesManager.class.getClassLoader().getResourceAsStream("resources/connectiondb.properties");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            logger.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param key is a key into properties file
     * @return {@code String} value from properties file by the key
     */
    public String get(String key) {
        return properties.getProperty(key);
    }
}
