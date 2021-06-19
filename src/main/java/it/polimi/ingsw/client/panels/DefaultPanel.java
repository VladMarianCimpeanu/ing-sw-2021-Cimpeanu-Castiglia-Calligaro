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
        if(error == ErrorMessage.notEnoughResources)
            setNotEnoughResource();
        if(error == ErrorMessage.productionUsed)
            setProductionUsed();
    }

    /**
     * Set the error label for a resource requirement not satisfied
     */
    public void setNotEnoughResource(){
        errorLabel.setText("You don't have enough resources to afford this!");
        repaint();
    }

    /**
     * Set the error label for a production already used in that turn
     */
    public void setProductionUsed(){
        errorLabel.setText("You have already used this production in this turn!");
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
