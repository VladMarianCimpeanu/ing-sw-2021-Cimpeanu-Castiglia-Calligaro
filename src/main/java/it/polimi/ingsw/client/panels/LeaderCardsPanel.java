package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.MessageToServer.ActivateLeaderCard;
import it.polimi.ingsw.client.MessageToServer.DiscardLeaderCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action panel that displays a button to activate or discard the selected leader card.
 */
public class LeaderCardsPanel extends ActionPanel implements ActionListener {
    private final int selectedLeaderCard;
    private final JButton discardButton;
    private final JButton activateButton;
    private final JLabel titleLabel = new JLabel("Choose an action for this card");

    public LeaderCardsPanel(int ID){
        super();
        selectedLeaderCard = ID;

        discardButton = new JButton("Discard");
        activateButton = new JButton("Activate");
        discardButton.addActionListener(this);
        activateButton.addActionListener(this);
        errorLabel = new JLabel("");
        this.add(titleLabel);
        this.add(activateButton);
        this.add(discardButton);
        this.add(errorLabel);
    }

    /**
     * if the pressed button is discardButton, it sends a DiscardMessage to the server.
     * if the pressed button is activateButton, it sends a ActivateLeaderCard to the server.
     * @param e click event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == discardButton) GUI.getClient().send(new DiscardLeaderCard(selectedLeaderCard));
        else if(e.getSource() == activateButton) GUI.getClient().send(new ActivateLeaderCard(selectedLeaderCard));
    }


    @Override
    public void displayError(ErrorMessage error) {
        errorLabel.setText(error.getCaption());
        revalidate();
    }
}
