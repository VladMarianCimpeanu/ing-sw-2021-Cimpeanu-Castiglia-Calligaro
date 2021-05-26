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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Debug");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        ScrollPanel scrollPanel = new ScrollPanel();
        frame.add(scrollPanel);

        frame.setVisible(true);
    }

    public MessagesPanel getMessagesPanel() {
        return messagesPanel;
    }
}
