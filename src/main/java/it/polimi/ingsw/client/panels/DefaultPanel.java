package it.polimi.ingsw.client.panels;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;

/**
 * panel that do not contains anything. It is used when the player has concluded an operation.
 */
public class DefaultPanel extends ActionPanel{

    @Override
    public void displayError(ErrorMessage error) {
    }
}
