package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.util.Map;

public class ResourceToPay extends MessageFromServer {
    private Map<Resource, Integer> resources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("Resources to pay:" + resources.toString());
    }
}
