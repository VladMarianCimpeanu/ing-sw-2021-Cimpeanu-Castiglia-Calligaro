package it.polimi.ingsw.client.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessagesPanel extends JPanel {
    public MessagesPanel() {
        setBackground(new Color(100,0,0));
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
    }

    public void addMessage(String message){
        JLabel label = new JLabel(message);
        label.setForeground(new Color(213, 213, 213, 255));
        add(label);
        revalidate();
        //repaint();
    }
}
