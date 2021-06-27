package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Resource;

import java.util.ArrayList;

/**
 * Message from server: it contains the remaining resources to redeem from market.
 */
public class ConvertedMarbles extends MessageFromServer{
    private ArrayList<Resource> resources;

    @Override
    public void activateMessage(Client client) {
        client.getGameView().updateResourcesFromMarket(resources);
    }
}
