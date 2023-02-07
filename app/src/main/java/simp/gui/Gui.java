package simp.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class Gui {

    // Class Constants
    private static final int WINDOW_WIDTH = 750;// pixels
    private static final int WINDOW_HEIGHT = 735;// pixels
    private static final int FIELD_WIDTH = 15;// characters
    private static final int AREA_WIDTH = 40;// characters
    private static final int BUTTON_HORIZONTAL_SPACING = 10;
    private static final int BUTTON_VERTICAL_SPACING = 10;
    private String APPLICATION_NAME = "Simple Inventory Manager";
    JFrame MAIN_WINDOW;
    public static final Color backgroundColor = new Color(70, 73, 75), foregroundColor = new Color(187, 187, 187);

    // Custom Class Instances
    protected static DropDownHandler connectionPanel;
    protected static JTextField searchWordField;
    protected static JSpinner threadCountField;
    protected static DropDownHandler dropDownHandler = new DropDownHandler();

    public Gui() {
        MAIN_WINDOW = new JFrame(APPLICATION_NAME);
        MAIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MAIN_WINDOW.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        MAIN_WINDOW.setJMenuBar(new SIMMenuBar());

        MAIN_WINDOW.setVisible(true);

    }
}
