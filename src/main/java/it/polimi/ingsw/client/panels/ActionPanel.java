package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public abstract class ActionPanel extends JPanel {
    private static final int width = 450;
    private static final int height = 450;
    protected JLabel errorLabel = new JLabel("");

    public ActionPanel() {
        setBackground(Color.WHITE);

        setBounds(1100, 0, width, height);
        setBorder(new EmptyBorder(20, 0, 0, 0));
    }

    public static int getActionWidth(){
        return width;
    }

    public static int getActionHeight(){
        return height;
    }

    public abstract void displayError(ErrorMessage error);

}
