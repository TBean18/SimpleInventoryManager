package simp.database;

import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.h2.tools.ChangeFileEncryption;
import org.h2.tools.RunScript;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import simp.App;
import simp.gui.Gui;
import simp.gui.SIMMenuBar;

@Slf4j
public class Db {

    @Getter
    static boolean isSignedIn = false;
    @NonNull
    static Connection connection;
    @Getter
    @Setter
    static File databaseFile;

    public static Connection getConnection() throws NullPointerException {
        if (connection == null) {
            throw new NullPointerException(
                    "Database connection is null. User has not yet successfully opened a database");
        }
        return connection;

    }

    public static boolean closeConnection() {
        if (connection == null) {
            return true;
        }
        try {
            connection.close();
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens the provided Database file.
     * Used to provision a new database if none exists at the provided path.
     * Sets Static Connection on successful connection
     */
    public static void openDatabase(File dbFile, char[] password) throws Exception {
        String filePath = dbFile.getAbsolutePath();
        String dbPath = filePath;
        if (filePath.substring(filePath.length() - 6).equals(".mv.db")) {
            dbPath = filePath.substring(0, filePath.length() - 6);
        }
        // String url = "jdbc:h2:" + System.getProperty("user.dir") + "/data.db";
        String url = "jdbc:h2:" + dbPath;

        log.info("Database URL: {}", url);

        try {
            connection = DriverManager.getConnection(url, "sa", new String(password));
            Arrays.fill(password, '0');
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("Metadata: {}", metaData.toString());
                runStartUpScript();
                isSignedIn = true;
            }
        } catch (SQLException e) {
            log.error("Failure to create new database connection", e);
            e.printStackTrace();
            throw new NullPointerException("Failed to Open DB with Error: " + e.getMessage());
        }

    }

    private static void runStartUpScript() {
        try {
            ResultSet res = RunScript.execute(connection,
                    new InputStreamReader(
                            Db.class.getClassLoader().getResourceAsStream("database/startUp.sql")));

            log.info("Start Up Script Ran successfully");
        } catch (SQLException e) {
            log.error("Unable to run start up script for database", e);
            e.printStackTrace();
        }
    }

}
