package simp.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class SIMMenuBar extends JMenuBar implements ActionListener {

    @Getter
    protected JMenu filesMenu;

    public SIMMenuBar() {
        super();
        this.filesMenu = new JMenu("Files");
        this.add(filesMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        log.info("MenuBar Interaction: {}", e.getActionCommand());

    }

}
