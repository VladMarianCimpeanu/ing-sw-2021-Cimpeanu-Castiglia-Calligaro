package it.polimi.ingsw.client.panels;

import javax.swing.*;
import java.awt.Color;

public class ActionPanel extends JPanel {
    public ActionPanel() {
        setBackground(Color.BLUE);

        setBounds( 1100, 0, 450, 450);

        add(new JButton("NON CLICCARMI (cu edition) !!!"));
    }
}
