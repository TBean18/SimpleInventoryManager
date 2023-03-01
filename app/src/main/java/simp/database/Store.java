package simp.database;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Store {
    public final static String STORE_ID = "STORE_ID";
    public final static String STORE_TITLE = "STORE_TITLE";
    public final static String STORE_DESCRIPTION = "STORE_DESCRIPTION";

    static final Map<Integer, Store> stores = new HashMap();

    int id;
    @Getter
    String title;
    @Getter
    Clob descriptionText;

    public Store(int id, String title, Clob descriptionText) {
        this.id = id;
        this.title = title;
        this.descriptionText = descriptionText;
    }

    public static Store getStore(int storeId, ResultSet rs) throws SQLException {
        if (stores.containsKey(storeId)) {
            return stores.get(storeId);
        }
        // Find and Store
        Store newStore = parseStoreRow(rs);
        stores.put(newStore.id, newStore);
        return newStore;
    }

    private static Store parseStoreRow(ResultSet rs) throws SQLException {
        Store ret = new Store(rs.getInt(STORE_ID), rs.getString(STORE_TITLE), rs.getClob(STORE_DESCRIPTION));
        return ret;
    }

}
