package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies the player that is obliged to use a specified strategy.
 */
public class AskStrategy extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Insert a strategy:");
    }
}
