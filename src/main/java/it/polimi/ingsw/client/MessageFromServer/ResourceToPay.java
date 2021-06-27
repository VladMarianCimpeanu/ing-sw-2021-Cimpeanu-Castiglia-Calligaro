package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Resource;

import java.util.Map;

/**
 * Message from server: it contains the resources that remains to pay by the client.
 */
public class ResourceToPay extends MessageFromServer {
    private Map<Resource, Integer> resources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().displayResourcesToPay(resources);
    }
}
