package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

/**
 * panel used to communicate to the player what he can do.
 */
public abstract class ActionPanel extends JPanel {
    private static final int width = 450;
    private static final int height = 450;
    protected JLabel errorLabel = new JLabel("");

    public ActionPanel() {
        setBackground(Color.WHITE);

        setBounds(1100, 0, width, height);
        setBorder(new EmptyBorder(20, 0, 0, 0));
    }

    /**
     * gives the width of the panel.
     * @return an integer representing the width of the panel.
     */
    public static int getActionWidth(){
        return width;
    }

    /**
     * gives the height of the panel.
     * @return an integer representing the height of the panel.
     */
    public static int getActionHeight(){
        return height;
    }

    /**
     * displays an error message if an error occurs.
     * @param error type of error occurred
     */
    public abstract void displayError(ErrorMessage error);

    /**
     * @return true if it is the first turn, otherwise return false.
     */
    public boolean isFirstTurn(){
        return false;
    }

}
