package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.benefit.Resource;

public class MarketStrategyStub extends MarketStrategy {
    public Resource resource;

    public MarketStrategyStub(){
        super(null, 0);
        resource = null;
    }

    public Resource getResource() {
        return resource;
    }
}
