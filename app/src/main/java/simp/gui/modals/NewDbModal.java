package simp.gui.modals;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import simp.Db;

@Slf4j
public class NewDbModal extends SIMModal {

    /**
     *
     */
    private static final String DATABASE_FIELD_PREFIX = "Database File: ";
    private static final String MODAL_TITLE = "Create a new Database";
    private static final String CHOOSE_FILE_BUTTON_LABEL = "Choose File";

    public NewDbModal(@NonNull JFrame owner) {
        super(owner, MODAL_TITLE, true);
        this.getContentPane().add(generateNewDatabaseWindow());
        this.setLocationRelativeTo(owner);
        this.pack();
        this.setVisible(true);

    }

    private Component generateNewDatabaseWindow() {
        JPanel ret = new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.PAGE_AXIS));

        // File Selection
        JPanel dataBaseFilePanel = new JPanel();
        JLabel databaseFileTextField = new JLabel(DATABASE_FIELD_PREFIX);
        JButton chooseFileButton = new JButton(CHOOSE_FILE_BUTTON_LABEL);
        chooseFileButton.addActionListener(this);
        dataBaseFilePanel.setLayout(new BoxLayout(dataBaseFilePanel, BoxLayout.LINE_AXIS));
        dataBaseFilePanel.add(databaseFileTextField);
        dataBaseFilePanel.add(Box.createHorizontalStrut(5));
        dataBaseFilePanel.add(chooseFileButton);
        ret.add(dataBaseFilePanel);

        // Password Selection and Confirmation
        ret.add(SIMModal.generatePasswordInputPanel("Encryption Password", this));
        ret.add(SIMModal.generatePasswordInputPanel("Confirm Password", this));

        ret.add(Box.createVerticalGlue());
        ret.add(SIMModal.generateConfirmationButtonsPanel(this));
        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SIMModal.CANCEL_BUTTON_ACTION_COMMAND:
                dispose();
                break;
            case CHOOSE_FILE_BUTTON_LABEL:
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                int response = fileChooser.showSaveDialog(this);
                if (response == JFileChooser.APPROVE_OPTION) {
                    Db.setDatabaseFile(fileChooser.getSelectedFile());
                }
                break;

            case SIMModal.OK_BUTTON_ACTION_COMMAND:
            case SIMModal.PASSWORD_INPUT_ACTION_COMMAND:
                // TODO Validation
                break;

            default:
                log.warn("Default leg of Action handler reached. Action Command: {}", e.getActionCommand());
                break;
        }

    }

}
