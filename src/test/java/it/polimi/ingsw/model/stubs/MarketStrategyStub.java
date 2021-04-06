package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.benefit.Resource;

public class MarketStrategyStub extends MarketStrategy {
    public Resource resource;

    public MarketStrategyStub(){
        super(null);
        resource = null;
    }

    public Resource getResource() {
        return resource;
    }
}
