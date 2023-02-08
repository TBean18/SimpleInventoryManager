package simp.gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import simp.Db;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class SIMMenuBar extends JMenuBar implements ActionListener {

    @Getter
    protected JMenu filesMenu;
    protected static final String FILE_MENU_LABEL = "File";

    protected JMenuItem newMenuItem;
    protected static final String NEW_MENU_ITEM_LABEL = "New";
    protected JMenuItem openMenuItem;
    protected static final String OPEN_MENU_ITEM_LABEL = "Open";

    public SIMMenuBar() {
        super();
        createFilesMenu();
        this.add(filesMenu);
    }

    private void createFilesMenu() {
        filesMenu = new JMenu(FILE_MENU_LABEL);
        filesMenu.addActionListener(this);

        // Menu Items
        newMenuItem = new JMenuItem(NEW_MENU_ITEM_LABEL, 'n');
        newMenuItem.addActionListener(this);
        filesMenu.add(newMenuItem);

        openMenuItem = new JMenuItem(OPEN_MENU_ITEM_LABEL, 'o');
        openMenuItem.addActionListener(this);
        filesMenu.add(openMenuItem);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("MenuBar Interaction: {}", e.getActionCommand());
        if (e.getSource() == openMenuItem) {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int response = fileChooser.showSaveDialog(null);
            if (response == fileChooser.APPROVE_OPTION) {
                Db.setDatabaseFile(fileChooser.getSelectedFile());
                new DbSignInModal(((JFrame) SwingUtilities.getWindowAncestor(this)), fileChooser.getSelectedFile());
                // TODO Do we want this here?
                // Db.getConnection();

            }

        }

    }

}
