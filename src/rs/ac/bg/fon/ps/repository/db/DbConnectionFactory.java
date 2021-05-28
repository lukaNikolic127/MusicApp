package rs.ac.bg.fon.ps.repository.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionFactory {

    private Connection connection;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("config/dbconfig.properties"));
            } catch (FileNotFoundException ex) {
                throw new Exception("Connecting to database failed!\nConfiguration file not found.");
            }
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            try {
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            } catch (SQLException ex) {
                throw new Exception("Server cannot process the request!");
            }
        }
        return connection;

    }
}
