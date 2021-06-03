package it.polimi.ingsw.client.panels;

import javax.swing.*;
import java.awt.*;

public class ScrollPanel extends JPanel {
    private MessagesPanel messagesPanel;
    public ScrollPanel() {
        setBounds(1100, 500, 400, 450);
        setLayout(new GridLayout());

        messagesPanel = new MessagesPanel();
        JScrollPane scrollable = new JScrollPane(messagesPanel);
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollable);
    }

    public MessagesPanel getMessagesPanel() {
        return messagesPanel;
    }
}
