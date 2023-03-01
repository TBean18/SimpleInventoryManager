package simp.gui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import lombok.Getter;
import simp.database.Inventory;

public class InventoryTable extends JTable {

    final static String[] COLUMN_LABELS = new String[] { "Item Title", "Store Title", "Quantity" };

    public static class InventoryTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return Inventory.inventoryCache.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Inventory.inventoryCache.get(rowIndex).item.getTitle();
                case 1:
                    return Inventory.inventoryCache.get(rowIndex).store.getTitle();
                case 2:
                    return Integer.valueOf(Inventory.inventoryCache.get(rowIndex).quantity);
                default:
                    return "ERROR";
            }
        }

        @Override
        public String getColumnName(int column) {
            return InventoryTable.COLUMN_LABELS[column];
        }
    }

    @Getter
    static InventoryTableModel tableModel = new InventoryTableModel();

    public InventoryTable() {
        super(tableModel);
    }

}
