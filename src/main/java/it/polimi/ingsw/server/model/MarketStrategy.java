package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * A Strategy activated by the matching leader card.
 * Can be passed to the market to select the resource to obtain from the white marbles.
 */
public class MarketStrategy {
    private Resource resource;
    private int ID;

    public MarketStrategy(Resource resource, int ID){
        this.resource = resource;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public Resource getResource() {
        return resource;
    }
}
