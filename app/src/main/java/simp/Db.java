package simp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Db {

    static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            createNewDatabase();
        }
        return connection;

    }

    /**
     * Used to provision a new database.
     * Sets Static Connection on successful connection
     */
    private static void createNewDatabase() {
        String url = "jdbc:h2:" + System.getProperty("user.dir") + "/data.db";

        log.info("Database URL: {}", url);

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("Metadata: {}", metaData.toString());
            }
        } catch (SQLException e) {
            log.error("Failure to create new database connection", e);
            e.printStackTrace();
        }

    }
}
