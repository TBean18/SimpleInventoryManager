package simp.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import simp.gui.InventoryTable;

@Slf4j
public class Inventory {

    public final static String INVENTORY_QUANTITY = "INVENTORY_QUANTITY";

    public int quantity;
    public Item item;
    public Store store;

    public final static List<Inventory> inventoryCache = new ArrayList<>();

    public Inventory(int quantity, Item item, Store store) {
        this.quantity = quantity;
        this.item = item;
        this.store = store;
    }

    public static List<Inventory> getInventoryCacheFromDb() {
        inventoryCache.clear();
        Connection conn = Db.getConnection();

        try {
            ResultSet res = conn.createStatement().executeQuery("""
                    SELECT * from Inventories
                    JOIN Items on Inventories.Item_ID = Items.ITEM_ID
                    JOIN Stores on Inventories.Store_ID = Stores.STORE_ID;
                    """);
            ResultSetMetaData md = res.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                log.info("Column Name: {}", md.getColumnLabel(i));
            }

            while (res.next()) {
                inventoryCache.add(parseInventoryRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error occurred while retrieving inventories", e);
        }

        InventoryTable.getTableModel().fireTableDataChanged();
        return inventoryCache;

    }

    static private Inventory parseInventoryRow(ResultSet rs) throws SQLException {
        int quantity = rs.getInt(Inventory.INVENTORY_QUANTITY);
        int itemId = rs.getInt(Item.ITEM_ID);
        Item item = Item.getItem(itemId, rs);
        Store store = Store.getStore(rs.getInt(Store.STORE_ID), rs);

        Inventory ret = new Inventory(quantity, item, store);
        return ret;
    }

}
