package simp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;
import simp.database.Db;
import simp.database.Inventory;

public class DbTest {

    File newDbFile = new File(System.getProperty("user.dir") + "/testDb.mv.db");

    @AfterEach
    public void deleteTempTestDatabase() {
        assertTrue(newDbFile.delete());
    }

    @Test
    public void testDbCreation() {
        // Create new DB File
        try {
            Db.openDatabase(newDbFile);
            assertTrue(Db.closeConnection());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testDbObjects() {
        try {
            Db.openDatabase(newDbFile);
            List<Inventory> allInventoriesFromDb = Inventory.getAllInventoriesFromDb();
            assertTrue(!allInventoriesFromDb.isEmpty());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
