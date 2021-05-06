package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

public class MarketState extends TurnState {

    public MarketState(Controller controller) {
        super(controller);
    }

    @Override
    public void putWarehouse(Resource resource, int shelf) {

    }

    @Override
    public void putExtraSlot(Resource resource) {

    }

    @Override
    public void discardRes(Resource resource) {

    }
}
