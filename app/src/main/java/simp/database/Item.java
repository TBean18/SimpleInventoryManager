package simp.database;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Item {

    public final static Map<Integer, Item> items = new HashMap<>();

    public final static String ITEM_ID = "ITEM_ID";
    public final static String ITEM_TITLE = "ITEM_TITLE";
    public final static String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public final static String ITEM_IMAGE = "ITEM_IMAGE";

    @Getter
    final int id;
    @Getter
    String title;
    @Getter
    Clob descriptionText;
    @Getter
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
        Item.items.put(this.id, this);
    }

    private Item(int id, String title, Clob descriptionText, Blob image) {
        this.id = id;
        this.title = title;
        this.descriptionText = descriptionText;
        this.image = image;
    }

    public void clearItemCacheMap() {
        Item.items.clear();
    }

    public static Item getItem(int itemId) throws SQLException {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        log.warn("Unable to find item requested in cache. ID = {}", itemId);
        return getItemFromDatabase(itemId);
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

    private static Item getItemFromDatabase(int id) throws SQLException {
        PreparedStatement stmt = Db.getConnection().prepareStatement("SELECT * FROM Items WHERE ITEM_id = ?",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            return parseItemRow(rs);
        throw new SQLException("Unable to find Item with Id = " + id);

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
        log.info("DB insertion for {}. Successful", item);

        // Get and return generated id
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Unable to retrieve generated keys");
        }

    }

    public String toString() {
        return String.format("Item: {id: %d, title: %s}", this.id, this.title);
    }

}
