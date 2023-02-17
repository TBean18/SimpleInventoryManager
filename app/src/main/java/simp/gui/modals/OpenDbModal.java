package simp.gui.modals;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import simp.Db;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Slf4j
public class OpenDbModal extends JDialog implements ActionListener {

    private static final String MODAL_LABEL = "Database Sign In";
    private static final String DATABASE_INFO_PREFIX = "Database Path: ";
    private static final String DATABASE_PASSWORD_LABEL = "Database Password: ";

    private final File dbFile;
    private JButton cancelButton = new JButton("Cancel");
    private JButton okButton = new JButton("Ok");
    private JPasswordField passwordInputField = new JPasswordField(12);

    public OpenDbModal(@NonNull JFrame owner, @NonNull File databaseFile) {
        super(owner, MODAL_LABEL, true);
        this.dbFile = databaseFile;
        this.getContentPane().add(generateSignInWindow());
        this.setLocationRelativeTo(owner);
        this.pack();
        this.setVisible(true);
    }

    private JPanel generateSignInWindow() {
        JPanel ret = new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));

        // First List the Database that the user is trying to log into
        JLabel dataBaseInfoLabel = new JLabel(DATABASE_INFO_PREFIX + dbFile.getAbsolutePath());
        ret.add(dataBaseInfoLabel);

        // Password
        JPanel passwordInputPanel = new JPanel();
        passwordInputPanel.setLayout(new BoxLayout(passwordInputPanel, BoxLayout.LINE_AXIS));
        passwordInputPanel.add(Box.createHorizontalGlue());
        JLabel dataBasePassLabel = new JLabel(DATABASE_PASSWORD_LABEL);
        passwordInputPanel.add(dataBasePassLabel);
        // Stop Text Fields from expanding vertically
        passwordInputField.setMaximumSize(passwordInputField.getPreferredSize());
        passwordInputField.addActionListener(this);
        passwordInputField.setActionCommand(SIMModal.PASSWORD_INPUT_ACTION_COMMAND);
        passwordInputPanel.add(passwordInputField);
        passwordInputPanel.add(Box.createHorizontalGlue());
        ret.add(passwordInputPanel);

        // Add Glue between the Labels and the Buttons
        ret.add(Box.createVerticalGlue());

        // Finally add the button panes
        JPanel buttons = SIMModal.generateConfirmationButtonsPanel(this);
        ret.add(buttons);

        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == SIMModal.CANCEL_BUTTON_ACTION_COMMAND) {
            log.info("Database login modal closed with Close Button");
            dispose();
        } else if (command == SIMModal.OK_BUTTON_ACTION_COMMAND
                || command == SIMModal.PASSWORD_INPUT_ACTION_COMMAND) {
            // TODO Implement Database Login
            Db.setDatabaseFile(dbFile);
            Db.getConnection();
            log.warn("Database Signin not yet implemented | {}", e.getActionCommand());
        }

    }

}
