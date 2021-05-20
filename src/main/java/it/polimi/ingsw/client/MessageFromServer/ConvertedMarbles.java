package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.server.model.market.Marble;

import java.util.ArrayList;
import java.util.Locale;

public class ConvertedMarbles extends MessageFromServer{
    private ArrayList<Resource> resources;

    @Override
    public void activateMessage(Client client) {
        String obtainedResources = "";
        for(Resource res: resources)
            obtainedResources += res.toString().toLowerCase(Locale.ROOT) + " ";
        client.getGameView().dumpMessage("These are the resources obtained from the market:\n" + obtainedResources);
    }
}
