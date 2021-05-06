package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;

public class BuyDevState extends TurnState {
    public BuyDevState(Controller controller) {
        super(controller);
    }

    @Override
    public void pay(Resource resource, String position) {

    }
}
