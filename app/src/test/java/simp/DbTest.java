package simp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;
import simp.database.Db;
import simp.database.Inventory;
import simp.database.Item;

public class DbTest {

    static File newDbFile = new File(System.getProperty("user.dir") + "/testDb.mv.db");
    static char[] newDbPassword = new char[] { '1', '2', '3' };
    Item testItem;

    @BeforeAll
    public static void createNewTestDatabase() {
        // Create new DB File
        try {
            Db.openDatabase(newDbFile, newDbPassword);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @AfterAll
    public static void deleteTempTestDatabase() {
        assertTrue(newDbFile.delete());
    }

    @Test
    public void testDbCreation() {
        // Create new DB File
        assertNotNull(Db.getConnection());

    }

    @Test
    public void testDbObjects() {
        try {
            List<Inventory> allInventoriesFromDb = Inventory.getAllInventoriesFromDb();
            assertTrue(!allInventoriesFromDb.isEmpty());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testItemCreation() {
        Clob itemDescription;
        testItem = null;
        try {
            itemDescription = Db.getConnection().createClob();
            itemDescription.setString(1, "Test Description");
            testItem = new Item("Unit Test Item", itemDescription, null);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(testItem);
    }

    @Test
    public void testItemRead() throws SQLException {
        if(testItem == null){
            testItemCreation();
        }
        Item.items.clear();
        Item readItem = Item.getItem(testItem.getId());
        assertEquals(testItem.getId(), readItem.getId());
    }

}
