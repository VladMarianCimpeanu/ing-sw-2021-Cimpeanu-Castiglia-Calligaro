package it.polimi.ingsw.client;

import javax.swing.*;
import java.awt.Color;

public class MessagesPanel extends JPanel {
    public MessagesPanel() {
        setBackground(Color.GREEN);

        setBounds(1100, 500, 450, 450);

        add(new JButton("NON CLICCARMI!"));
    }
}
