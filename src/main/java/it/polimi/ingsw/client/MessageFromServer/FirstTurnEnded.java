package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies that the first turn has just ended.
 */
public class FirstTurnEnded extends MessageFromServer{
    @Override
    public void activateMessage(Client client) {
        client.getGameView().firstTurnEnded();
    }
}
