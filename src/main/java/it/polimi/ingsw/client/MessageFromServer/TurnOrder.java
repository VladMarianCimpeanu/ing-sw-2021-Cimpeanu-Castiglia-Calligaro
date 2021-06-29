package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: notify the client how many resources can redeem at beginning of the game.
 */
public class TurnOrder extends MessageFromServer {
    private int position;
    private int numberOfResources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().chooseResources(position, numberOfResources);
    }
}
