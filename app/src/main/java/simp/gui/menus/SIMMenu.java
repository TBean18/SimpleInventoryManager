package simp.gui.menus;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import lombok.NonNull;
import simp.database.Db;

public abstract class SIMMenu extends JMenu implements MenuListener {

    private boolean dbRequired;

    public SIMMenu(@NonNull String menuLabel, boolean dbNeeded) {
        super(menuLabel);
        this.dbRequired = dbNeeded;
        this.addMenuListener(this);
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void menuDeselected(MenuEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (dbRequired) {
            for (int i = 0; i < this.getItemCount(); i++) {
                this.getItem(i).setEnabled(Db.isSignedIn());
            }
        }
    }

}
