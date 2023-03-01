package simp.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;

import lombok.Getter;
import lombok.extern.java.Log;

public class Gui {

    private static final int WINDOW_WIDTH = 750;// pixels
    private static final int WINDOW_HEIGHT = 735;// pixels
    private static final int FIELD_WIDTH = 15;// characters
    private static final int AREA_WIDTH = 40;// characters
    private static final int BUTTON_HORIZONTAL_SPACING = 10;
    private static final int BUTTON_VERTICAL_SPACING = 10;
    private String APPLICATION_NAME = "Simple Inventory Manager";
    @Getter
    static JFrame MAIN_WINDOW;
    public static final Color backgroundColor = new Color(70, 73, 75), foregroundColor = new Color(187, 187, 187);

    public Gui() {
        MAIN_WINDOW = new JFrame(APPLICATION_NAME);
        MAIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MAIN_WINDOW.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MAIN_WINDOW.setJMenuBar(new SIMMenuBar());
        MAIN_WINDOW.add(new JScrollPane(new InventoryTable()));

        MAIN_WINDOW.setVisible(true);

    }

}
