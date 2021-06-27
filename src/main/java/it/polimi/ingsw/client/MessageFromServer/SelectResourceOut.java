package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies the client that he can select the output resources for the base production.
 */
public class SelectResourceOut extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        client.getGameView().startExtraProd();
    }
}
