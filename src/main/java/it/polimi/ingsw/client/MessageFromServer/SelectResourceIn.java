package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;

/**
 * Message from server: it notifies the client that he can select the input resources for base & extra production.
 */
public class SelectResourceIn extends MessageFromServer {
    @Override
    public void activateMessage(Client client) {
        client.getGameView().startBaseProd();
    }
}
