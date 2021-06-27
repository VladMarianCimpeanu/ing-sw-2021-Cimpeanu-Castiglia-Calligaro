package it.polimi.ingsw.server.MessageToClient;

import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Marble;

import java.util.ArrayList;

/**
 * Message to client: it contains the remaining resources to redeem from market.
 */
public class ConvertedMarbles implements MessageToClient{
    private String type;
    private ArrayList<Resource> resources;

    public ConvertedMarbles(ArrayList<Resource> resources) {
        this.type = "ConvertedMarbles";
        this.resources = resources;
    }
}
