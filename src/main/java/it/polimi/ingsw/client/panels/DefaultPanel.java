package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;

import javax.swing.*;
import java.awt.*;

/**
 * panel that do not contains anything. It is used when the player has concluded an operation.
 */
public class DefaultPanel extends ActionPanel{
    private JLabel errorLabel;

    public DefaultPanel() {
        errorLabel = new JLabel();
        errorLabel.setBounds(20, 30, 200, 20);
        add(errorLabel);
    }
    @Override
    public void displayError(ErrorMessage error) {
        System.out.println(error);
        if(error == ErrorMessage.notEnoughResources)
            setNotEnoughResource();
    }

    /**
     * Set the error label for a resource requirement not satisfied
     */
    public void setNotEnoughResource(){
        errorLabel.setText("You don't have enough resources to afford this purchase!");
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
