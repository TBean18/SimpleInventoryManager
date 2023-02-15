package simp;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.ChangeFileEncryption;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Db {

    static Connection connection;
    @Getter
    @Setter
    static File databaseFile;

    public static Connection getConnection() {
        if (connection == null) {
            createNewDatabase(databaseFile.getAbsolutePath());
        }
        return connection;

    }

    /**
     * Used to provision a new database.
     * Sets Static Connection on successful connection
     */
    private static void createNewDatabase(@NonNull String filePath) {
        String dbPath = filePath;
        if (filePath.substring(filePath.length() - 6).equals(".mv.db")) {
            dbPath = filePath.substring(0, filePath.length());
        }
        // String url = "jdbc:h2:" + System.getProperty("user.dir") + "/data.db";
        String url = "jdbc:h2:" + dbPath;

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
