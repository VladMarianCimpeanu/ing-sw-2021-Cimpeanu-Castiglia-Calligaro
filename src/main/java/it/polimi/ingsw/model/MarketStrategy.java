package it.polimi.ingsw.model;

import it.polimi.ingsw.model.benefit.Resource;

public class MarketStrategy {
    private Resource resource;

    public MarketStrategy(Resource resource){
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
