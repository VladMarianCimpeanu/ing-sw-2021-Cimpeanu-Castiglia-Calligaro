package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

/**
 * A Strategy activated by the matching leader card.
 * Can be passed to the market to select the resource to obtain from the white marbles.
 */
public class MarketStrategy {
    private Resource resource;

    public MarketStrategy(Resource resource){
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
