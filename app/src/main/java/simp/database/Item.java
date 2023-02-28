package simp.database;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    final int id;
    String title;
    Clob descriptionText;
    Blob image;

    /**
     * 
     * @param title
     * @param descriptionText
     * @param image           Binary data representing the item image
     * @throws SQLException Possibly thrown on storing the item in the database
     */
    public Item(String title, Clob descriptionText, Blob image) throws SQLException {
        this.title = title;
        this.descriptionText = descriptionText;
        this.image = image;
        // Insert and acquire the unique final id
        this.id = Item.insertItemIntoDatabase(this);
    }

    private Item(int id, String title, Clob descriptionText, Blob image) {
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

    private static int insertItemIntoDatabase(Item item) throws NullPointerException, SQLException {
        PreparedStatement statement = Db.getConnection()
                .prepareStatement("INSERT INTO Items (ITEM_Title, ITEM_Description, ITEM_Image) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, item.title);
        statement.setClob(2, item.descriptionText);
        statement.setBlob(3, item.image);
        int numRows = statement.executeUpdate();
        if (numRows != 1) {
            // Failed to add new row
            throw new SQLException(
                    "Affected rows = " + numRows + ". When 1 is expected for inserting an item into the database");
        }

        // Get and return generated id
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Unable to retrieve generated keys");
        }

    }

}
