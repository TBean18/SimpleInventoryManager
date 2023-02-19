package simp.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import simp.database.Db;

public class ItemsMenu extends SIMMenu implements ActionListener {

    private static final String ITEMS_MENU_LABEL = "Items";

    public ItemsMenu() {
        super(ITEMS_MENU_LABEL, true);

        JMenuItem newItemMenuItem = new JMenuItem("New Item");
        newItemMenuItem.addActionListener(this);
        newItemMenuItem.setEnabled(Db.isSignedIn());
        this.add(newItemMenuItem);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
