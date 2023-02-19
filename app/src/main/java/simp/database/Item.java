package simp.database;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Item {

    public final static Map<Integer, Item> items = new HashMap<>();

    public final static String ITEM_ID = "ITEM_ID";
    public final static String ITEM_TITLE = "ITEM_TITLE";
    public final static String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public final static String ITEM_IMAGE = "ITEM_IMAGE";

    int id;
    String title;
    Clob descriptionText;
    Blob image;

    public Item(int id, String title, Clob descriptionText, Blob image) {
        this.id = id;
        this.title = title;
        this.descriptionText = descriptionText;
        this.image = image;
    }

    public static Item getItem(int itemId) throws NullPointerException {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        log.warn("Unable to find item requested. ID = {}", itemId);
        throw new NullPointerException("Unable to find Item with Id = " + itemId);
    }

    public static Item getItem(int itemId, ResultSet rs) throws SQLException {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        return parseItemRow(rs);

    }

    /**
     * 
     * @param rs - Result set, on current row to parse
     * @return Item representation of current row info
     * @throws SQLException If encountered while getting data from ResultSet
     */
    public static Item parseItemRow(ResultSet rs) throws SQLException {
        int itemId = rs.getInt(ITEM_ID);
        Item ret = new Item(itemId, rs.getString(ITEM_TITLE), rs.getClob(ITEM_DESCRIPTION),
                rs.getBlob(ITEM_IMAGE));
        return ret;
    }

}
