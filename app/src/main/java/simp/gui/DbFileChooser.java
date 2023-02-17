package simp.gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DbFileChooser extends JFileChooser {
    public DbFileChooser() {
        super(System.getProperty("user.dir"));
        this.removeChoosableFileFilter(getAcceptAllFileFilter());
        this.addChoosableFileFilter(new FileNameExtensionFilter("H2 Database File", "db"));
    }

}
