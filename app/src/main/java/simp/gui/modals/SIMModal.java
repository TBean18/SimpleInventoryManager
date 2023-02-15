package simp.gui.modals;

import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import lombok.NonNull;

public abstract class SIMModal extends JDialog implements ActionListener {

    /**
     * Custom wrapper class used for the generatePasswordInputPanel function.
     */
    public static class PasswordInputPanel extends JPanel {

        public JPasswordField passwordField = new JPasswordField(12);

        public PasswordInputPanel(String passwordLabel, ActionListener actionListener) {
            super();
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            this.add(Box.createHorizontalGlue());
            JLabel dataBasePassLabel = new JLabel(passwordLabel);
            this.add(dataBasePassLabel);
            // Stop Text Fields from expanding vertically
            passwordField.setMaximumSize(passwordField.getPreferredSize());
            passwordField.addActionListener(actionListener);
            passwordField.setActionCommand(PASSWORD_INPUT_ACTION_COMMAND);
            this.add(passwordField);
            this.add(Box.createHorizontalGlue());

        }

        public char[] getPassword() {

            return passwordField.getPassword();
        }
    }

    // Class Constants
    public static final String OK_BUTTON_ACTION_COMMAND = "OK BUTTON COMMAND";
    public static final String CANCEL_BUTTON_ACTION_COMMAND = "CANCEL";
    /**
     * Action Command String used to denote actions performed on the JPasswordField
     */
    static final String PASSWORD_INPUT_ACTION_COMMAND = "Password Input";

    public SIMModal(@NonNull JFrame owner, @NonNull String title, boolean modality) {
        super(owner, title, modality);
        this.setLocationRelativeTo(owner);
    }

    public static PasswordInputPanel generatePasswordInputPanel(String passwordLabel, ActionListener actionListener) {
        return new PasswordInputPanel(passwordLabel, actionListener);
    }

    public static JPanel generateConfirmationButtonsPanel(ActionListener actionListener) {
        JPanel buttons = new JPanel();
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionListener);
        cancelButton.setActionCommand(CANCEL_BUTTON_ACTION_COMMAND);
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(actionListener);
        okButton.setActionCommand(OK_BUTTON_ACTION_COMMAND);

        // Layouts
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(okButton);
        buttons.add(Box.createHorizontalGlue());

        return buttons;

    }

}
