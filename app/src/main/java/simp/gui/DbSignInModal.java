package simp.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lombok.NonNull;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DbSignInModal extends JDialog implements ActionListener {

    private static final String MODAL_LABEL = "Database Sign In";
    private final File dbFile;

    public DbSignInModal(@NonNull JFrame owner, @NonNull File databaseFile) {
        super(owner, MODAL_LABEL, true);
        this.dbFile = databaseFile;
        this.getContentPane().add(generateSignInWindow());
        this.pack();
        this.setVisible(true);
    }

    private JPanel generateSignInWindow() {
        JPanel ret = new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));

        // Finally add the button panes
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        JButton cancelButton = new JButton("Cancel");
        buttons.add(cancelButton);
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(this);
        buttons.add(okButton);

        ret.add(buttons);

        return ret;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
