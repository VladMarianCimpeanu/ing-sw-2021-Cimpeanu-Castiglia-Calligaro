package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Resource;

import java.util.ArrayList;

/**
 * Message from server: it contains the resources that remains to take from the market.
 */
public class ResourceToPut extends MessageFromServer {
    private ArrayList<Resource> resources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().dumpMessage("The Resources obtained from the market are: "+resources.toString());
    }
}
